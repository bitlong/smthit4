/**
 * 
 */
package cn.smthit.v4.common.lang.data;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Bean
 *
 */
@Data
public class PageParamDTO extends ParamDTO {
    /**
     * 当前页
     */
    private int pageNo;

    /**
     * 每页大小
     */
    private int pageSize;
}
