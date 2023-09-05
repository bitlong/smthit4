package cn.smthit.v4.redis.id;

import org.junit.Test;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/5
 */
public class TestID {
    @Test
    public void testId() {
        String str = String.format("%06d", 12311123);
        System.out.println(str);

        str = String.format("%06d", 11);
        System.out.println(str);
    }

}
