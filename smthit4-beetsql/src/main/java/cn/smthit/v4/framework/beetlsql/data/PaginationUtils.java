/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.data;

import cn.hutool.db.PageResult;
import cn.smthit.v4.common.lang.convert.AbstractConvert;
import org.beetl.sql.core.engine.PageQuery;


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

	public static <VO, PO> Pagination<VO> convertPagination(PageResult<PO> page, AbstractConvert<PO, VO> convert) {
		return new Pagination<VO>()
				.setCurrentPage((int)page.getPage())
				.setTotal(page.getTotal())
				.setTotalPages((int)page.getTotalPage())
				.setRows(convert.toVOs(page));
	}

	public static <VO> Pagination<VO> convertPagination(PageQuery<VO> page) {
		return new Pagination<VO>()
				.setCurrentPage((int)page.getPageNumber())
				.setTotal(page.getTotalRow())
				.setTotalPages((int)page.getTotalPage())
				.setRows(page.getList());
	}
}
