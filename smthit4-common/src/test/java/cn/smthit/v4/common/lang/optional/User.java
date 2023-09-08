package cn.smthit.v4.common.lang.optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/5
 */
@ToString
@Data
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
}
