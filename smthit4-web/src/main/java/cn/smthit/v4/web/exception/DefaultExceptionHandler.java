package cn.smthit.v4.web.exception;

import cn.smthit.v4.common.lang.data.Result;
import cn.smthit.v4.common.lang.exception.AssertException;
import cn.smthit.v4.common.lang.exception.DalException;
import cn.smthit.v4.common.lang.exception.ErrorCode;
import cn.smthit.v4.common.lang.exception.ServiceException;
import cn.smthit.v4.common.lang.kits.GsonKit;
import cn.smthit.v4.web.kits.WebKit;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

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
public abstract class DefaultExceptionHandler {

    final static String DEFAULT_ERROR_MSG = "当前请求出现错误,请重试或者联系管理员";

    /**
     * ServiceException, Exception异常和Error的处理
     *
     * @author haoyun.zheng
     */
    @ExceptionHandler(value = {ServiceException.class, Exception.class, Error.class})
    public Object serviceException(Throwable throwable, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HandleResult handleResult = handleExtraException(throwable, request, response);
        if(handleResult.handled) {
            return handleResult.result;
        }

        StringBuffer sb = new StringBuffer();

        // 异常信息可以增加采集机制 类似于Sentry
        if (throwable instanceof ServiceException) {
            ServiceException exp = (ServiceException) throwable;
            sb.append(throwable.getMessage());
            log.info("业务访问, 异常信息：" + throwable.getMessage(), throwable);
            String msg = Optional.ofNullable(exp.getMessage()).orElse("服务访问异常");
            String msgDetail = Optional.ofNullable(exp.getDetailMessage()).orElse("服务访问异常，未提供错误明细，请联系管理员");
            return outputException(msg, msgDetail, exp.getCode(), throwable, request, response);
        } else if(throwable instanceof AssertException) {
            AssertException exp = (AssertException) throwable;
            String msg = Optional.ofNullable(exp.getMessage()).orElse("数据验证失败");
            String msgDetail = Optional.ofNullable(exp.getDetailMessage()).orElse("数据验证失败,请检查接口参数是否正确");
            return outputException(msg, msgDetail, exp.getCode(), throwable, request, response);
        } else if(throwable instanceof DalException) {
            DalException exp = (DalException) throwable;
            String msg = Optional.ofNullable(exp.getMessage()).orElse("数据操作失败");
            String msgDetail = Optional.ofNullable(exp.getDetailMessage()).orElse("数据操作失败,请检查业务逻辑是否正确");
            return outputException(msg, msgDetail, exp.getCode(), throwable, request, response);
        } else {
            sb.append(String.format("未处理异常 (%s)", throwable.getMessage()));
            log.error("未处理异常, 异常信息：" + throwable.getMessage(), throwable);
            return outputException("服务不可用，请查看明细，联系管理员解决", sb.toString(), ErrorCode.DEFAULT_ERROR.getValue(), throwable, request, response);
        }
    }

    /**
     * 重载该方法，添加默认的处理逻辑
     * @param throwable
     * @param request
     * @param response
     * @return
     */
    public abstract HandleResult handleExtraException(Throwable throwable, HttpServletRequest request, HttpServletResponse response) throws IOException;
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

        return outputException("接口参数验证失败", sj.toString(), null, exp, request, response);
    }

    protected Object outputException(String message, String detailMessage, String code,
                                   Throwable throwable,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        message = Optional.ofNullable(message).orElse("服务访问异常");
        detailMessage = Optional.ofNullable(detailMessage).orElse("服务操作异常，请联系管理员");

        if (WebKit.isAjaxRequest(request) || WebKit.isJsonRequest(request)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter printWriter = response.getWriter();

            Result<?> result = Result.failed()
                    .message(message)
                    .detailMessage(detailMessage)
                    .code(Optional.ofNullable(code).orElse(Result.DEFAULT_ERROR));

            printWriter.write(GsonKit.toJson(result));
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
            modelAndView.addObject("message", message);
            modelAndView.addObject("detailMessage", detailMessage);
            modelAndView.addObject("exception", throwable);
            modelAndView.addObject("stackTrace", stackTrace);

            modelAndView.setViewName("/errors/500");
            return modelAndView;
        }
    }
}
