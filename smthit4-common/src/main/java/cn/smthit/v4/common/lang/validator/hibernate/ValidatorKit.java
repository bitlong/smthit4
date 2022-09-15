package cn.smthit.v4.common.lang.validator.hibernate;



import org.hibernate.validator.HibernateValidator;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/16  22:06
 */
public class ValidatorKit {

    public static Validator getValidator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }
}