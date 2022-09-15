package cn.smthit.v4.common.lang.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/16  22:09
 */
@AllArgsConstructor
@Data
public class SmthitResult<T> {
    private boolean success;
    private String message;
    private T data;
    private Map<String, String> errors = new HashMap<>();

    public SmthitResult() {
        this.success = true;
        this.message = "success";
    }

    public boolean hasError() {
        return !success;
    }

    public String toSimple() {
        String result = StringUtils.EMPTY;

        if(!success) {
            StringJoiner sj = new StringJoiner(",");
            getErrors().forEach((key, value)->{
                sj.add(key + ": " + value);
            });

            result = sj.toString();
        }

        return result;
    }
}