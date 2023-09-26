package cn.smthit.v4.common.lang.sql;

import cn.smthit.v4.common.lang.exception.ServiceException;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.ws.Service;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/26
 */
public class TestSQLInject {

    @Test
    public void testSQL() {
        String sql = "1=1";
        boolean r = SqlInjectKit.checkSqlParam(sql);
        Assert.assertEquals(r, true);

        try {
            sql = "; delete table";
            SqlInjectKit.checkXssSqlParam(sql);
        } catch (ServiceException exp) {
            System.out.println(exp);
        }
    }
}
