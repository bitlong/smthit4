package cn.smthit.v4.web.core;

import cn.smthit.v4.common.lang.core.IParam;
import cn.smthit.v4.common.lang.core.RangeCondition;
import cn.smthit.v4.common.lang.core.SetCondition;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/8
 */
@Data
public class UserQueryParam extends IParam {
    /**
     * 等值查询
     */
    @NotNull
    private Long id;

    /**
     * 范围查询
     */
    @NotNull
    private RangeCondition<Date> createDate;

    @NotNull
    private Integer age;

    /**
     * 范围查询  min <= x <= max
     */
    @NotEmpty
    private RangeCondition<Integer> age2;

    /**
     * 多分段查询，多个条件之间使用 or
     */
    @NotEmpty
    private List<RangeCondition<Integer>> age3;

    /**
     * 集合查询（in）
     */
    @NotEmpty
    private SetCondition<Integer> ids;
}
