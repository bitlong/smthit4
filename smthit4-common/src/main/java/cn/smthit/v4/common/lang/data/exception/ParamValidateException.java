/**
 * 
 */
package cn.smthit.v4.common.lang.data.exception;

import cn.smthit.v4.common.lang.exception.ServiceException;

/**
 * @author Bean
 *
 */
public class ParamValidateException extends ServiceException {
	private static final long serialVersionUID = 130940167912437540L;

	public ParamValidateException(String errorCode, String msg, Throwable exception) {

		super(errorCode, msg, exception);
	}

	public ParamValidateException(String errorCode, String msg) {
		super(errorCode, msg);
	}

	public ParamValidateException(String msg, Throwable exception) {
		super(msg, exception);
	}
}
