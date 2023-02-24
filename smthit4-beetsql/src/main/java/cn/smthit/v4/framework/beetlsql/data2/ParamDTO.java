/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.data2;

import cn.smthit.v4.common.lang.validator.CheckKit;

/**
 * @author Bean
 *
 */
public class ParamDTO implements IParamDTO {

	protected ParamDTO() {
	}

	/**
	 * 验证参数的正确性
	 * 
	 * @param group
	 */
	@Override
	public void validate(Class<?> group) throws ParamValidateException {
		CheckKit.check(this, group, true);
	}

	/**
	 * 验证参数的正确性
	 */
	@Override
	public void validate() throws ParamValidateException {
		CheckKit.check(this, true);
	}

}
