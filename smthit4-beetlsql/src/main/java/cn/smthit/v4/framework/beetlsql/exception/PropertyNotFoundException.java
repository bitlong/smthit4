package cn.smthit.v4.framework.beetlsql.exception;


import cn.smthit.v4.common.lang.exception.ServiceException;

/**
 * 属性不存在的异常
 * @author Bean
 *
 */
public class PropertyNotFoundException extends ServiceException {
	private static final long serialVersionUID = -4292743712467260622L;

	public PropertyNotFoundException(String msg) {
		super(msg);
	}

	public PropertyNotFoundException(String errorCode, String msg) {
		super(errorCode, msg);
	}

	public PropertyNotFoundException(String msg, Throwable exception) {
		super(msg, exception);
	}

	public PropertyNotFoundException(String errorCode, String msg, Throwable exception) {
		super(errorCode, msg, exception);
	}

}
