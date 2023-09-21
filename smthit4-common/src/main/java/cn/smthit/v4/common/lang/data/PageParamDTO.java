/**
 * 
 */
package cn.smthit.v4.common.lang.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author Bean
 *
 */
@Getter
public class PageParamDTO<T> extends ParamDTO {
    /**
     * 当前页
     */
    private int pageNumber;

    /**
     * 每页大小
     */
    private int pageSize;

    public T setPageNumber(int pageNo) {
        this.pageNumber = pageNo;
        return (T)this;
    }

    public T setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return (T)this;
    }
}
