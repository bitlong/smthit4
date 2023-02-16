/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.exception;

import cn.smthit.v4.common.lang.exception.ServiceException;
import com.baidu.unbiz.fluentvalidator.Result;

import lombok.Getter;

/**
 * @author Bean
 *
 */
public class ParamException extends ServiceException {
	private static final long serialVersionUID = 130940167912437540L;

	@Getter
	private Result result;
	
	public ParamException(String errorCode, String msg, Throwable exception) {
		super(errorCode, msg, null);
	}

	public ParamException(String errorCode, String msg) {
		super(errorCode, msg, null);
	}

	public ParamException(String msg, Throwable exception) {
		super(msg);
	}

	public ParamException(String msg, Result result) {
		super(msg);
		this.result = result;
	}

}
