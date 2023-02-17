/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.data;

import cn.smthit.v4.common.lang.convert.AbstractConvert;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.engine.PageQueryFuntion;
import org.beetl.sql.core.page.PageResult;


/**
 * @author Bean
 *
 */
public class PaginationUtils {

	private PaginationUtils() {
	}

	
	public static <VO, PO> Pagination<VO> convertPagination(Pagination<PO> page) {
		return new Pagination<VO>()
				.setCurrentPage(page.getCurrentPage())
				.setTotal(page.getTotal())
				.setTotalPages(page.getTotalPages());	
	}

	public static <VO, PO> Pagination<VO> convertPagination(PageResult<PO> pageResult, int pageNumber, AbstractConvert<PO, VO> convert) {
		return new Pagination<VO>()
				.setCurrentPage(pageNumber)
				.setTotal(pageResult.getTotalRow())
				.setTotalPages((int)pageResult.getTotalPage())
				.setRows(convert.toVOs(pageResult.getList()));
	}

	public static <VO, PO> Pagination<VO> convertPagination(PageQuery<PO> page, AbstractConvert<PO, VO> convert) {
		return new Pagination<VO>()
				.setCurrentPage((int)page.getPageNumber())
				.setTotal(page.getTotalRow())
				.setTotalPages((int)page.getTotalPage())
				.setRows(convert.toVOs(page.getList()));
	}


	public static <VO> Pagination<VO> convertPagination(PageQuery<VO> page) {
		return new Pagination<VO>()
				.setCurrentPage((int)page.getPageNumber())
				.setTotal(page.getTotalRow())
				.setTotalPages((int)page.getTotalPage())
				.setRows(page.getList());
	}
}
