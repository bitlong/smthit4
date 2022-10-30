package cn.smthit.v4.common.lang.validator;


import cn.smthit.v4.common.lang.exception.AssertException;
import cn.smthit.v4.common.lang.exception.ErrorBuilder;
import cn.smthit.v4.common.lang.exception.ErrorCode;
import cn.smthit.v4.common.lang.validator.hibernate.ValidatorKit;

import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/16  22:08
 */
public  class SmthitValidator<T> {

    public SmthitResult<?> check() {
        return check(null, false);
    }

    public SmthitResult<?> check(Boolean throwException) {
        return check(null, throwException);
    }

    public SmthitResult<?> check(Class<?> group, Boolean throwException) {
        SmthitResult<?> result = check(group);
        if(result.hasError() && throwException) {
            throw ErrorBuilder.builder()
                    .setCode(ErrorCode.ASSERT_FAILED)
                    .setDetailMessage(result.toSimple())
                    .build(AssertException.class);
        }
        return result;
    }

    public SmthitResult<?> check(Class<?> group) {
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

    public SmthitResult<?> checkProperty(String propertyName) {
        return checkProperty(null, propertyName);
    }

    public SmthitResult<?> checkProperty(Class<?> group, String propertyName) {
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