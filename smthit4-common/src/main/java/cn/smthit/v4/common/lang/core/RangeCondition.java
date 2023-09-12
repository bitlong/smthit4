package cn.smthit.v4.common.lang.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/8
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RangeCondition<T> implements Serializable {
    private T min;
    private T max;
}
