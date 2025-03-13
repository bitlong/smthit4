package cn.smthit.v4.common.lang.validator.hibernate;




import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;

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
