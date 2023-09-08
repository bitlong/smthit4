package cn.smthit.v4.web.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/8
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageQueryParam extends QueryParam {

    private Integer pageSize = 30;

    private Integer pageNo = 1;

}
