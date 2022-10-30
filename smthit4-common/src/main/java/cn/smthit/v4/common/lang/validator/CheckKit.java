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
 * @date: 2022/10/30  11:41
 */
public class CheckKit {

    public static <T> SmthitResult<?> check(T object, Boolean throwException) {
        return check(object, null, throwException);
    }

    public static <T> SmthitResult<?> check(T object, Class<?> group, Boolean throwException) {
        SmthitResult<T> result =  check(object, group);

        if(result.hasError() && throwException) {
            throw ErrorBuilder.builder()
                    .setCode(ErrorCode.ASSERT_FAILED)
                    .setDetailMessage(result.toSimple())
                    .build(AssertException.class);
        } else {
            return result;
        }
    }

    private static <T> SmthitResult<T> check(T object, Class<?> group) {

        SmthitResult result = new SmthitResult();
        Set<ConstraintViolation<T>> setCV = ValidatorKit.getValidator().validate(object,
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