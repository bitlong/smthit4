/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.data2;

import cn.smthit.v4.common.lang.exception.ServiceException;
import com.baidu.unbiz.fluentvalidator.Result;
import lombok.Getter;

/**
 * @author Bean
 *
 */
public class ParamValidateException extends ServiceException {
	private static final long serialVersionUID = 130940167912437540L;

	@Getter
	private Result result;
	
	public ParamValidateException(String errorCode, String msg, Throwable exception) {

		super(errorCode, msg, exception);
	}

	public ParamValidateException(String errorCode, String msg) {
		super(errorCode, msg);
	}

	public ParamValidateException(String msg, Throwable exception) {
		super(msg, exception);
	}

	public ParamValidateException(String msg, Result result) {
		super(msg);
		this.result = result;
	}

}
