package cn.smthit.v4.common.lang.data;

import cn.smthit.v4.common.lang.enums.IEnumStatus;
import cn.smthit.v4.common.lang.exception.ServiceException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/9/16  10:39
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class Result<T> implements Serializable {
    public static final String OK = "ok";
    public static final String DEFAULT_ERROR = "500";

    private boolean success;
    private String code;
    private String message;
    private String detailMessage;

    private T data;

    public static <T> Result<T> ok() {
        Result<T> result = new Result();

        result.code = OK;
        result.success = true;
        result.message = "Success";

        return result;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result();

        result.code = OK;
        result.success = true;
        result.message = "Success";
        result.data = data;

        return result;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    public static <T>  Result<T> failed() {
        Result result = new Result();
        result.code = DEFAULT_ERROR;
        result.message = "Faided";
        return result;
    }

    public static <T> Result<T> failed(ServiceException exp) {
        Result result = new Result();
        result.success = false;
        result.code = exp.getMessage();
        result.message = exp.getMessage();
        return result;
    }

    public Result<T> error(IEnumStatus<String> error) {
        this.code = error.getValue();
        this.message = error.getDesc();
        return this;
    }

    public Result<T> error(String code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public Result<T> code(String code) {
        this.code = code;
        return this;
    }

    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    public Result<T> detailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }
}