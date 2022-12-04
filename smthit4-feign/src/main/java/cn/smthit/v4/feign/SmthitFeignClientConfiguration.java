package cn.smthit.v4.feign;

import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/12/3  16:01
 */
@Configuration
public class SmthitFeignClientConfiguration {

    /**
     * 默认重用次数
     * @return
     */
    @Primary
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }

    /**
     * 请求默认拦截器，再Header里增加Feign请求头
     * @return
     */
    @Bean
    public RequestInterceptor exceptionRequestInterceptor() {
        return new ExceptionRequestInterceptor();
    }

    /**
     * 异常的解码器
     * @return
     */
    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new FeignExceptionErrorDecoder();
    }
}