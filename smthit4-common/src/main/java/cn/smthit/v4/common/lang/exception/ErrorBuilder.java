package cn.smthit.v4.common.lang.exception;

import cn.smthit.v4.common.lang.enums.EnumStatus;
import cn.smthit.v4.common.lang.enums.IEnumStatus;
import lombok.experimental.Accessors;

import java.lang.reflect.Constructor;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/9/9  11:51
 */
@Accessors(chain = true)
public class ErrorBuilder {

    private IEnumStatus<String> code;
    private String detailMessage;

   public  static ErrorBuilder builder() {
        return new ErrorBuilder();
    }

    public ErrorBuilder setCode(IEnumStatus<String> code) {
        this.code = code;
        return this;
    }

    public ErrorBuilder appendDetailMessage(String message, Object...args) {
        String formattedMsg = String.format(message, args);
        if(detailMessage != null) {
            this.detailMessage += ", " + formattedMsg;
        } else {
            this.detailMessage = formattedMsg;
        }
        return this;
    }

    public ErrorBuilder setDetailMessage(String detailMessage, Object...args) {
       String formattedMsg = String.format(detailMessage, args);
       this.detailMessage = formattedMsg;
       return this;
    }

    public <T extends ServiceException> T build(Class<T> cls) {
        T instance = null;
        try {
            Constructor constructor = cls.getConstructor(String.class);
            if(constructor != null) {
                instance = (T)constructor.newInstance(detailMessage);
            } else {
                throw new AssertException("异常对象(" + getClass().getName() + ")必须实现'" + cls.getSimpleName() + "(String arg)'的构造方法");
            }

            instance.setCode(code.getValue());
            instance.setMessage(code.getDesc());

        } catch (Exception e) {
        }

        return instance;
    }
}