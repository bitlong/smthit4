package cn.smthit.v4.common.lang.validator;

import cn.smthit.v4.common.lang.validator.data.Person;
import org.junit.Test;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/11/26  9:37
 */

public class TestAtLeastOneNotNull {
    @Test
    public void testOne() {
        Person p = new Person("Bean", 13);
        CheckKit.check(p, true);
    }
}