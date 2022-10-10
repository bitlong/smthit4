package cn.smthit.v4.feign.exception;

import cn.smthit.v4.common.lang.exception.ServiceException;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/10/10  23:54
 */
public class FeignServerException extends ServiceException {
    public FeignServerException(String detailMessage) {
        super(detailMessage);
    }
}