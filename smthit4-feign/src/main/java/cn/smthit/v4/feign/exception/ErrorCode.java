package cn.smthit.v4.feign.exception;

import cn.smthit.v4.common.lang.enums.IEnumStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/9/16  13:55
 */
@Getter
@AllArgsConstructor
public enum ErrorCode implements IEnumStatus<String>  {

    HTTP_40X("http-40x", "HTTP 40X 错误"),
    HTTP_50X("http-50x", "HTTP 50X粗偶");

    private String value;
    private String desc;
}