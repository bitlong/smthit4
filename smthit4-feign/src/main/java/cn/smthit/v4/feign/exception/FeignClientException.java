package cn.smthit.v4.feign.exception;

import cn.smthit.v4.common.lang.exception.ServiceException;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/10/10  23:53
 */
public class FeignClientException extends ServiceException {
    public FeignClientException(String detailMessage) {
        super(detailMessage);
    }
}