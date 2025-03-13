package cn.smthit.v4.framework.beetlsql.data;

import cn.smthit.v4.common.lang.convert.AbstractConvert;
import cn.smthit.v4.common.lang.convert.DefaultConvert;
import cn.smthit.v4.common.lang.data.PageData;
import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;

/**
 * PageData对象
 * @author bean
 * @date 2023/9/12
 */
public class PageDataKit {

    public static PageRequest<?> of(int pageNumber, int pageSize) {
        return DefaultPageRequest.of(pageNumber, pageSize);
    }

    public static <PO> PageData<PO> convertPage(PageResult<PO> pageResult, int pageNumber) {
        return new PageData<PO>()
                .setCurrentPage(pageNumber)
                .setTotal(pageResult.getTotalRow())
                .setPageCount((int)pageResult.getTotalPage())
                .setRows(pageResult.getList());
    }

    public static <VO, PO> PageData<VO> convertPage(PageResult<PO> pageResult, int pageNumber,
                                                AbstractConvert<PO, VO> convert) {
        return new PageData<VO>()
                .setCurrentPage(pageNumber)
                .setTotal(pageResult.getTotalRow())
                .setTotal((int)pageResult.getTotalPage())
                .setRows(convert.toVOs(pageResult.getList()));
    }

    public static <VO, PO> PageData<VO> convertPage(PageResult<PO> pageResult, int pageNumber, Class<VO> voCls) {
        DefaultConvert<PO, VO> dc = new DefaultConvert<>(voCls);
        return new PageData<VO>()
                .setCurrentPage(pageNumber)
                .setTotal(pageResult.getTotalRow())
                .setPageCount((int)pageResult.getTotalPage())
                .setRows(dc.toVOs(pageResult.getList()));
    }
}
