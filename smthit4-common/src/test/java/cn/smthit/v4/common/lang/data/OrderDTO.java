package cn.smthit.v4.common.lang.data;

import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/12
 */
@Data
public class OrderDTO extends ParamDTO {
    private Long id;

    private RangeCondition<Date> registerDate;

    private SetCondition<Long> ids;
}
