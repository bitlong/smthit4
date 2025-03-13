/**
 *
 */
package cn.smthit.v4.framework.beetlsql.data;

import cn.smthit.v4.framework.beetlsql.exception.ParamException;


/**
 * @author Bean
 *
 */
public interface IParamDTO {
	/**
	 * 验证参数的正确性
	 *
	 * @param group
	 */
	default public void validate(Class<?> group) throws ParamException {
		throw new UnsupportedOperationException();
	}

	/**
	 * 验证参数的正确性
	 */
	default public void validate() throws ParamException {
		throw new UnsupportedOperationException();
	}

}
