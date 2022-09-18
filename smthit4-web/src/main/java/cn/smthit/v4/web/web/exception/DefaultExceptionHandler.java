package cn.smthit.v4.web.web.exception;

import cn.smthit.v4.common.lang.data.ResponseData;
import cn.smthit.v4.common.lang.exception.ServiceException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.StringJoiner;

/**
 * 控制器异常处理
 *
 * @author haoyun.zheng
 */
@Slf4j
public class DefaultExceptionHandler {

    final static String DEFAULT_ERROR_MSG = "当前请求出现错误,请重试或者联系管理员";

    /**
     * ServiceException, Exception异常和Error的处理
     *
     * @author haoyun.zheng
     */
    @ExceptionHandler(value = {ServiceException.class, Exception.class, Error.class})
    public Object serviceException(Throwable throwable, HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.warn("默认异常处理拦截, 异常信息：" + throwable.getMessage(), throwable);

        StringJoiner sj = new StringJoiner(";");

        //TODO
        if (throwable instanceof ClientAbortException) {
            sj.add("ClientAbortException, " + throwable.getMessage());
        } else if (throwable instanceof ArithmeticException) {
            sj.add("数学计算异常, 请检查数学计算公式是否正确, " + throwable.getMessage());
        } else if (throwable instanceof ServiceException) {
            sj.add("业务异常, " + throwable.getMessage());
        } else {
            sj.add(String.format("其他截住的异常, requestUrl = %s, queryStr = %s", request.getRequestURL(),
                    request.getQueryString(), throwable.getMessage()));
        }

        return printException(sj.toString(), throwable, request, response);
    }

    /**
     * 全局异常中处理BindException，并返回
     * @param exp
     * @return
     */
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    public Object handleBindException(BindException exp, HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.warn("接口参数验证异常, URL={}", request.getRequestURL());

        StringJoiner sj = new StringJoiner(";");
        // 获取所有字段验证出错的信息
        exp.getFieldErrors().forEach(error -> {
            sj.add(error.getField() + ":" + error.getDefaultMessage());
        });

        return printException(sj.toString(), exp, request, response);
    }

    protected boolean isAjaxRequest(HttpServletRequest request) {
        return request.getHeader("X-Requested-With") != null &&
                "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    protected Object printException(String message, Throwable throwable, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isAjaxRequest(request)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter printWriter = response.getWriter();

            String msg = throwable.getMessage();
            if (StringUtils.isEmpty(msg)) {
                msg = DEFAULT_ERROR_MSG;
            }

            printWriter.write(new Gson().toJson(ResponseData.newFailed(msg)));
            printWriter.flush();
            printWriter.close();

            return new ModelAndView();
        } else {

            StringWriter stringWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(stringWriter);
            throwable.printStackTrace(writer);
            StringBuffer buffer = stringWriter.getBuffer();

            String stackTrace = buffer.toString();
            if (StringUtils.isEmpty(stackTrace)) {
                stackTrace = DEFAULT_ERROR_MSG;
            }

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("exception", throwable);
            modelAndView.addObject("message", message);
            modelAndView.addObject("stackTrace", stackTrace);

            modelAndView.setViewName("/errors/500");
            return modelAndView;
        }
    }
}