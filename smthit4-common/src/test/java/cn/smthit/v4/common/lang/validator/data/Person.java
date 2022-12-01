package cn.smthit.v4.common.lang.validator.data;

import cn.smthit.v4.common.lang.validator.hibernate.AtLeastOneNotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.apache.http.client.utils.Rfc3492Idn;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/11/26  9:38
 */
@AllArgsConstructor
@ToString
@Data
@AtLeastOneNotNull(fieldNames = {"name", "age"}, message = "name和age至少有一个不为空")
public class Person {
    private String name;
    private Integer age;
}