package cn.smthit.v4.common.lang.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/8
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SetCondition<T> implements Serializable  {
    /**
     * 集合的值
     */
    private Set<T> value = new HashSet<>();
}
