package cn.smthit.v4.feign;

import cn.smthit.v4.feign.exception.FeignExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


/**
 * @description: ...
 * @author: Bean
 * @date: 2022/12/3  16:01
 */
@Configuration
public class SmthitFeignServerConfiguration implements WebMvcConfigurer {

    /**
     * 全局异常拦截器
     * @param resolvers
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        FeignExceptionHandler exceptionHandle = new FeignExceptionHandler();
        exceptionHandle.setOrder(1);
        resolvers.add(exceptionHandle);
    }
}