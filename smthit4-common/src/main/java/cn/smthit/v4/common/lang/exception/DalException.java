package cn.smthit.v4.common.lang.exception;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/10/12  19:45
 */
public class DalException extends ServiceException {

    public DalException(String detailMessage) {
        super(detailMessage);
        setCode(ErrorCode.DAL_ERROR.getValue());
        setMessage(ErrorCode.DAL_ERROR.getDesc());
        setDetailMessage(detailMessage);
    }
}