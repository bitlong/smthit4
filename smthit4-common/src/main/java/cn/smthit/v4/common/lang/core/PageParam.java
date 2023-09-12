package cn.smthit.v4.common.lang.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bean
 * @date 2023/9/8
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageParam implements IParam {

    private Integer pageSize = 30;

    private Integer pageNo = 1;

}
