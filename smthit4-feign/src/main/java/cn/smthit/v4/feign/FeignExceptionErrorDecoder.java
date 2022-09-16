package cn.smthit.v4.feign;

import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * @description: FeignClient异常解码器
 * @author: Bean
 * @date: 2022/9/16  12:23
 */
public class FeignExceptionErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        return null;
    }
}