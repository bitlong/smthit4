/**
 * 
 */
package com.smthit.framework.dal.data;

import javax.validation.Validation;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;
import com.smthit.framework.dal.exception.ParamException;

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

		Result result = FluentValidator.checkAll(group)
				.on(this, new HibernateSupportedValidator<>().setHiberanteValidator(Validation.buildDefaultValidatorFactory().getValidator()))
				.doValidate()
				.result(ResultCollectors.toSimple());

		if (!result.isSuccess()) {
			throw new ParamException("参数验证失败:" + result.getErrors().toString(), result);
		}
	}

	/**
	 * 验证参数的正确性
	 */
	default public void validate() throws ParamException {
		Result result = FluentValidator.checkAll()
				.on(this, new HibernateSupportedValidator<>().setHiberanteValidator(Validation.buildDefaultValidatorFactory().getValidator()))
				.doValidate()
				.result(ResultCollectors.toSimple());

		if (!result.isSuccess()) {
			throw new ParamException("参数验证失败:" + result.getErrors().toString(), result);
		}
	}

}
