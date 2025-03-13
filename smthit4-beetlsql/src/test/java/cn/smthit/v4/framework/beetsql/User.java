package cn.smthit.v4.framework.beetsql;

import cn.smthit.v4.framework.beetlsql.data.ParamDTO;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/12
 */
@Data
@Builder
public class User extends ParamDTO<User> {
    private Long id;
    private String name;
}
