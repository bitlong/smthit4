package cn.smthit.v4.framework.beetsql;

import cn.smthit.v4.common.lang.kits.GsonKit;
import cn.smthit.v4.framework.beetlsql.data.ParamDTO;
import org.junit.Test;

import javax.jws.soap.SOAPBinding;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/12
 */
public class TestData {

    @Test
    public void testParam() {

        User dto = User.builder()
                .id(1L)
                .name("bean")
                .build();

        System.out.println(GsonKit.toJson(dto));
    }

    @Test
    public void testPageParam() {
        PageUser pageUser = new PageUser();

        pageUser.setId(1L);
        pageUser.setName("Bean");
        pageUser.setPageNumber(1);
        pageUser.setPageSize(1);

        System.out.println(GsonKit.toJson(pageUser));
    }
}
