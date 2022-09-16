package cn.smthit.v4.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/9/16  12:28
 */
public class ExceptionRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //异常序列化
        requestTemplate.header(FeignConstants.FEIGN_REQUEST_HEADER, FeignConstants.FEIGN_REQUEST_HEADER_VALUE);

    }
}