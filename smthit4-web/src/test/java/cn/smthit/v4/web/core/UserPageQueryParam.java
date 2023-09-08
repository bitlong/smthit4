package cn.smthit.v4.web.core;

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
public class UserPageQueryParam extends PageQueryParam {
    @NotNull
    private RangeCondition<Date> createDate;
}
