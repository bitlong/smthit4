/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.data2;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;

import javax.validation.Validation;

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

		Result result = FluentValidator.checkAll(group)
				.on(this, new HibernateSupportedValidator<>().setHiberanteValidator(Validation.buildDefaultValidatorFactory().getValidator()))
				.doValidate()
				.result(ResultCollectors.toSimple());

		if (!result.isSuccess()) {
			throw new ParamValidateException("参数验证失败:" + result.getErrors().toString(), result);
		}
	}

	/**
	 * 验证参数的正确性
	 */
	@Override
	public void validate() throws ParamValidateException {
		Result result = FluentValidator.checkAll()
				.on(this, new HibernateSupportedValidator<>().setHiberanteValidator(Validation.buildDefaultValidatorFactory().getValidator()))
				.doValidate()
				.result(ResultCollectors.toSimple());

		if (!result.isSuccess()) {
			throw new ParamValidateException("参数验证失败:" + result.getErrors().toString(), result);
		}
	}

}
