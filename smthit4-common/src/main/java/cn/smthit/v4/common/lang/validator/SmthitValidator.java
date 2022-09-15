package cn.smthit.v4.common.lang.validator;

import com.sgai.common.lang.validator.hibernate.ValidatorKit;

import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/16  22:08
 */
public  class SmthitValidator<T> {

    public SmthitResult check() {
        return check(null);
    }

    public SmthitResult check(Class<?> group) {
        SmthitResult result = new SmthitResult();
        Set<ConstraintViolation<SmthitValidator<T>>> setCV = ValidatorKit.getValidator().validate(this,
                group == null ? Default.class : group);
        if(!setCV.isEmpty()) {
            result.setSuccess(false);
            result.setMessage("Valid Failed!");
            setCV.forEach( item -> {
                result.getErrors().put(item.getPropertyPath().toString(), item.getMessage());
            });
        }

        return result;
    }

    public SmthitResult checkProperty(String propertyName) {
        return checkProperty(null, propertyName);
    }

    public SmthitResult checkProperty(Class<?> group, String propertyName) {
        SmthitResult result = new SmthitResult();

        Set<ConstraintViolation<SmthitValidator<T>>> setCV = ValidatorKit.getValidator().validateProperty(this, propertyName,
                group == null ? Default.class : group);


        if(!setCV.isEmpty()) {
            result.setSuccess(false);
            result.setMessage("Valid Failed!");
            setCV.forEach( item -> {
                result.getErrors().put(item.getPropertyPath().toString(), item.getMessage());
            });
        }

        return result;
    }
}