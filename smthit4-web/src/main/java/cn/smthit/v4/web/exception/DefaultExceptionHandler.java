package cn.smthit.v4.web.exception;

import cn.smthit.v4.common.lang.data.Result;
import cn.smthit.v4.common.lang.exception.AssertException;
import cn.smthit.v4.common.lang.exception.ServiceException;
import cn.smthit.v4.common.lang.kits.GsonKit;
import cn.smthit.v4.web.kits.WebKit;
import com.sun.javafx.binding.StringFormatter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
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

        StringBuffer sb = new StringBuffer();

        // 异常信息可以增加采集机制 类似于Sentry
        if (throwable instanceof ServiceException) {
            sb.append(throwable.getMessage());
            log.info("业务访问, 异常信息：" + throwable.getMessage(), throwable);
        } else {
            sb.append(StringFormatter.format("未处理异常 (%s)", throwable.getMessage()));
            log.error("未处理异常, 异常信息：" + throwable.getMessage(), throwable);
        }

        return outputException(sb.toString(), throwable, request, response);
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

        return outputException(sj.toString(), exp, request, response);
    }

    private Object outputException(String message, Throwable throwable, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (WebKit.isAjaxRequest(request) || WebKit.isJsonRequest(request)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter printWriter = response.getWriter();

            String msg = throwable.getMessage();
            if (StringUtils.isEmpty(msg)) {
                msg = DEFAULT_ERROR_MSG;
            }

            //以下用语可以做成配置项
            if(throwable instanceof ServiceException) {
                ServiceException exp = (ServiceException) throwable;
                Result<?> result = Result.failed(exp);
                result.message(Optional.ofNullable(exp.getMessage()).orElse("服务访问异常"));
                result.detailMessage(Optional.ofNullable(exp.getDetailMessage()).orElse("服务操作异常，请联系管理员"));
                printWriter.write(GsonKit.toJson(result));
            } else if(throwable instanceof AssertException) {
                AssertException exp = (AssertException) throwable;
                Result<?> result = Result.failed(exp);
                result.message(Optional.ofNullable(exp.getMessage()).orElse("数据验证失败"));
                result.detailMessage(Optional.ofNullable(exp.getDetailMessage()).orElse("数据验证失败,请检查接口参数是否正确"));
            } else {
                Result<?> result = Result.failed();
                result.message("接口访问异常, 当前服务不可用");
                result.detailMessage(Optional.ofNullable(throwable.getMessage()).orElse("接口操作异常，请联系管理员"));
                printWriter.write(GsonKit.toJson(result));
            }

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