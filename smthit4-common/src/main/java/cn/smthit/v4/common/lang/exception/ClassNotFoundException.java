package cn.smthit.v4.common.lang.exception;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/9/16  13:47
 */
public class ClassNotFoundException extends ServiceException {

    public ClassNotFoundException(String detailMessage) {
        super(detailMessage);
        setCode(ErrorCode.DATA_PARSE_ERROR.getValue());
        setMessage(ErrorCode.DATA_PARSE_ERROR.getDesc());
        setDetailMessage(detailMessage);
    }
}