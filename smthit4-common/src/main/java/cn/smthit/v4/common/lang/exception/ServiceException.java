package cn.smthit.v4.common.lang.exception;


import lombok.Data;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/9/15  15:10
 */
@Data
public class ServiceException extends RuntimeException {
    private String code;
    private String message;
    private String detailMessage;

    public ServiceException(String detailMessage) {
        super(detailMessage);
        this.code = ErrorCode.DEFAULT_ERROR.getValue();
        this.message = ErrorCode.DEFAULT_ERROR.getDesc();
        this.detailMessage = detailMessage;
    }
    public ServiceException(String code, String message, String detailMessage) {
        super(detailMessage);
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
    }
}