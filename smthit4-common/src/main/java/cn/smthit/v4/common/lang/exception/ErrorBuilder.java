package cn.smthit.v4.common.lang.exception;

import cn.smthit.v4.common.lang.enums.EnumStatus;
import cn.smthit.v4.common.lang.enums.IEnumStatus;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/9/9  11:51
 */
@Slf4j
@Accessors(chain = true)
public class ErrorBuilder {

    private IEnumStatus<String> code;
    private String message;
    private Throwable throwable;
    private StringBuffer detailMessage = new StringBuffer();

   public  static ErrorBuilder builder() {
        return new ErrorBuilder();
    }

    public ErrorBuilder setCode(IEnumStatus<String> code) {
        this.code = code;
        return this;
    }

    public ErrorBuilder setMessage(String message) {
       this.message = message;
       return this;
    }

    public ErrorBuilder appendDetailMessage(String message, Object...args) {
        String formattedMsg = String.format(message, args);
        if(detailMessage.length() > 0) {
            detailMessage.append("\n");
        }
        detailMessage.append(formattedMsg);
        return this;
    }

    public ErrorBuilder setDetailMessage(String detailMessage, Object...args) {
       String formattedMsg = String.format(detailMessage, args);
       this.detailMessage = new StringBuffer();
       this.detailMessage.append(formattedMsg);
       return this;
    }

    public ErrorBuilder setParentException(Throwable exp) {
       this.throwable = exp;
        return this;
    }

    public <T extends ServiceException> T build(Class<T> cls) {
        T instance = null;
        try {
            Constructor constructor = cls.getConstructor(String.class);
            if(constructor != null) {
                instance = (T)constructor.newInstance(detailMessage.toString());
            } else {
                throw new AssertException("异常对象(" + getClass().getName() + ")必须实现'" + cls.getSimpleName() + "(String arg)'的构造方法");
            }

            if(code != null) {
                instance.setCode(code.getValue());
                instance.setMessage(code.getDesc());
            }

            if(message != null) {
                instance.setMessage(instance.getMessage() == null ? message : instance.getMessage() + ", " + message);
            }

            if(throwable != null) {
                instance.addSuppressed(throwable);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return instance;
    }
}