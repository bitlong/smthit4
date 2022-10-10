package cn.smthit.v4.feign;

import cn.smthit.v4.feign.exception.FeignExceptionHandler;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/9/16  12:27
 */
@Configuration
public class FeignConfiguration implements WebMvcConfigurer {

    @Primary
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }

    @Bean
    public RequestInterceptor exceptionRequestInterceptor() {
        return new ExceptionRequestInterceptor();
    }

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new FeignExceptionErrorDecoder();
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        FeignExceptionHandler exceptionHandle = new FeignExceptionHandler();
        exceptionHandle.setOrder(1);
        resolvers.add(exceptionHandle);
    }
}