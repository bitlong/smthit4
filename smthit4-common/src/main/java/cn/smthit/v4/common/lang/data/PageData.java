package cn.smthit.v4.common.lang.data;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

@Data
@Accessors(chain = true)
public class PageData<T> {
    /**
     * 当前页
     */
    private long currentPage;

    /**
     * 总条数
     */
    private long total;
    /**
     * 当前页数据
     */
    private List<T> rows;
    /**
     * 总页数
     */
    private long pageCount;

    public PageData() {
        this.rows = Collections.emptyList();
    }

    public PageData(int currentPage, int pageCount, long total, List<T> rows) {
        this.total = total;
        this.currentPage = currentPage;
        this.rows = rows;
        this.pageCount = pageCount;
    }
}
