package cn.smthit.v4.mybatis.plus;


import cn.smthit.v4.common.lang.convert.AbstractConvert;
import cn.smthit.v4.common.lang.convert.DefaultConvert;
import cn.smthit.v4.common.lang.data.PageData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/15  18:26
 */
public class PageKit {
    /**
     * 创建一个Page对象
     * @param <T>
     * @return
     */
    public static <T> PageData<T> newPage() {
        return new PageData<T>();
    }

    public static <T> PageData<T> newPage(int currentPage, int pageCount, long total, List<T> rows) {
        return new PageData<T>(currentPage, pageCount, total, rows);
    }

    public static <PO, VO> PageData<VO> newPage(Page page, AbstractConvert<PO, VO> convert) {

        PageData<VO> pageData = new PageData<>();

        pageData.setPageCount(page.getPages());
        pageData.setTotal(page.getTotal());
        pageData.setCurrentPage(page.getCurrent());

        pageData.setRows(convert.toVOs(page.getRecords()));

        return pageData;
    }

    public static <PO, VO> PageData<VO> newPage(Page page, Class<VO> voCls) {
        DefaultConvert<PO, VO> dc = new DefaultConvert<>(voCls);

        PageData<VO> pageData = new PageData<>();

        pageData.setPageCount(page.getPages());
        pageData.setTotal(page.getTotal());
        pageData.setCurrentPage(page.getCurrent());
        pageData.setRows(dc.toVOs(page.getRecords()));

        return pageData;
    }

    public static <PO, VO> PageData<VO> newPage(Page page) {

        PageData<VO> pageData = new PageData<>();

        pageData.setPageCount(page.getPages());
        pageData.setTotal(page.getTotal());
        pageData.setCurrentPage(page.getCurrent());

        return pageData;
    }
}