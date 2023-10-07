package cn.smthit.v4.mybatis.plus;

import cn.smthit.v4.mybatis.plus.interceptor.SqlInjectInterceptor;
import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 * @author bean
 * @date 2023/10/7
 */
public class SqlInjectInterceptorTest {

    @Test
    public void testSplit() {
        SqlInjectInterceptor inter = new SqlInjectInterceptor();
        System.out.println(inter.KEYWORDS);
        for(int i = 0; i < inter.KEYWORDS.length; i++) {
            System.out.println(String.format("i = %d v = %s", i, inter.KEYWORDS[i]));
        }
    }
}
