package cn.smthit.v4.feign.exception;

import cn.smthit.v4.common.lang.exception.ErrorBuilder;
import cn.smthit.v4.common.lang.exception.ErrorCode;
import cn.smthit.v4.common.lang.exception.ServiceException;
import cn.smthit.v4.feign.FeignConstants;
import cn.smthit.v4.feign.kits.SerializalbeKit;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.nio.charset.StandardCharsets;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/9/16  11:59
 */
@Slf4j
public class ExceptionHandle implements HandlerExceptionResolver, Ordered {
    @Setter
    private int order = Ordered.LOWEST_PRECEDENCE;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exp) {
        log.warn(String.format("信息信息: %s", exp.getMessage()), exp);

        String value = request.getHeader(FeignConstants.FEIGN_REQUEST_HEADER);
        //是一个Feign请求，需要序列化异常信息
        if(FeignConstants.FEIGN_REQUEST_HEADER_VALUE.equals(value)) {
            response.addHeader(FeignConstants.FEIGN_REQUEST_HEADER, FeignConstants.FEIGN_REQUEST_HEADER_VALUE);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

            //出现异常的方法
            HandlerMethod method = (HandlerMethod) handler;

            boolean pass = check(exp, method);
            //pass = true 为业务型异常，= false为非业务型异常，统一处理
            Exception exception = pass ? exp : ErrorBuilder.builder()
                    .setCode(ErrorCode.DEFAULT_ERROR)
                    .setDetailMessage(ExceptionUtils.getStackTrace(exp))
                    .build(ServiceException.class);

            try {
                IOUtils.write(SerializalbeKit.serialize(exception), response.getOutputStream());
            } catch (IOException e) {
            }
            return new ModelAndView();
        }

        try {
            String errorMsg = String.format("%s : %s", exp.getClass().getName(), exp.getMessage());
            IOUtils.write(errorMsg, response.getOutputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            //ignore
        }

        return new ModelAndView();
    }

    @Override
    public int getOrder() {
        return order;
    }

    /**
     * @param exp
     * @param method
     * @return
     */
    private boolean check(Exception exp, HandlerMethod method) {
        if (exp instanceof ServiceException) {
            return true;
        }

        return false;
    }
}