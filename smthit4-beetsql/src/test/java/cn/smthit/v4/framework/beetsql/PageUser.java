package cn.smthit.v4.framework.beetsql;

import cn.smthit.v4.framework.beetlsql.data.PageParamDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageUser extends PageParamDTO {
    private Long id;
    private String name;
}
