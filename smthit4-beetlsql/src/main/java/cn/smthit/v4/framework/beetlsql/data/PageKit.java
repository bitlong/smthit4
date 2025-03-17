/**
 *
 */
package cn.smthit.v4.framework.beetlsql.data;

import cn.smthit.v4.common.lang.convert.AbstractConvert;
import cn.smthit.v4.common.lang.convert.DefaultConvert;
import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.core.page.PageResult;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Bean
 * 生成Page对象
 */
public class PageKit {

	private PageKit() {
	}

	public static PageRequest<?> of(int pageNumber, int pageSize) {
		return DefaultPageRequest.of(pageNumber, pageSize);
	}

	public static <VO, PO> Page<VO> newPage(ModelMapper mapper,
											PageResult<PO> pageResult,
											int pageNumber,
											Class<VO> cls) {
		List<VO> rows = pageResult.getList().stream().map(item->mapper.map(item, cls)).collect(Collectors.toList());
		return new Page<VO>()
				.setTotalPages((int)pageResult.getTotalPage())
				.setCurrentPage(pageNumber)
				.setTotal(pageResult.getTotalRow())
				.setRows(rows);

	}

	public static <PO> Page<PO> convertPage(Page<PO> page) {
		return new Page<PO>()
				.setCurrentPage(page.getCurrentPage())
				.setTotal(page.getTotal())
				.setTotalPages(page.getTotalPages());
	}

	public static <VO, PO> Page<VO> convertPage(Page<PO> page, AbstractConvert<PO, VO> convert) {
		return new Page<VO>()
				.setCurrentPage(page.getCurrentPage())
				.setTotal(page.getTotal())
				.setTotalPages(page.getTotalPages())
				.setRows(convert.toVOs(page.getRows()));
	}

	public static <VO, PO> Page<VO> convertPage(Page<PO> page, Class<VO> voCls) {
		DefaultConvert<PO, VO> dc = new DefaultConvert<>(voCls);
		return new Page<VO>()
				.setCurrentPage(page.getCurrentPage())
				.setTotal(page.getTotal())
				.setTotalPages(page.getTotalPages())
				.setRows(dc.toVOs(page.getRows()));
	}

	public static <PO> Page<PO> convertPage(PageResult<PO> pageResult, int pageNumber) {
		return new Page<PO>()
				.setCurrentPage(pageNumber)
				.setTotal(pageResult.getTotalRow())
				.setTotalPages((int)pageResult.getTotalPage())
				.setRows(pageResult.getList());
	}

	public static <VO, PO> Page<VO> convertPage(PageResult<PO> pageResult, int pageNumber, AbstractConvert<PO, VO> convert) {
		return new Page<VO>()
				.setCurrentPage(pageNumber)
				.setTotal(pageResult.getTotalRow())
				.setTotalPages((int)pageResult.getTotalPage())
				.setRows(convert.toVOs(pageResult.getList()));
	}

	public static <VO, PO> Page<VO> convertPage(PageResult<PO> pageResult, int pageNumber, Class<VO> voCls) {
		DefaultConvert<PO, VO> dc = new DefaultConvert<>(voCls);
		return new Page<VO>()
				.setCurrentPage(pageNumber)
				.setTotal(pageResult.getTotalRow())
				.setTotalPages((int)pageResult.getTotalPage())
				.setRows(dc.toVOs(pageResult.getList()));
	}
}
