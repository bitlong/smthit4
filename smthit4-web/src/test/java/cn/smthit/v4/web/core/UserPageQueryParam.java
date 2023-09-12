package cn.smthit.v4.web.core;

import cn.smthit.v4.common.lang.core.PageParam;
import cn.smthit.v4.common.lang.core.RangeCondition;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/8
 */
@Data
public class UserPageQueryParam extends PageParam {
    @NotNull
    private RangeCondition<Date> createDate;
}
