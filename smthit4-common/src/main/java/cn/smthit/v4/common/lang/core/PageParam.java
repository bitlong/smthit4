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
public class PageParam implements IPageParam {

    private int pageSize = 30;

    private int pageNo = 1;
}
