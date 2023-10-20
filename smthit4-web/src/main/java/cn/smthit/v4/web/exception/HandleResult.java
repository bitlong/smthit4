package cn.smthit.v4.web.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO
 *
 * @author bean
 * @date 2023/10/20
 */
@AllArgsConstructor
@Data
public class HandleResult {
    boolean handled;
    Object result;
}
