/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.data2;


import cn.smthit.v4.common.lang.kits.ClassKit;
import cn.smthit.v4.common.lang.kits.Property;
import cn.smthit.v4.common.lang.kits.StringKit;
import cn.smthit.v4.framework.beetlsql.data.EnumOrder;
import cn.smthit.v4.framework.beetlsql.data.OrderBy;
import cn.smthit.v4.framework.beetlsql.exception.PropertyNotFoundException;
import lombok.Data;
import lombok.Getter;

/**
 * @author Bean 翻页查询的参数对象
 */
@Data
@lombok.experimental.Accessors(chain = true)
public class PageQueryParam<C extends QueryParamDTO> {
	/**
	 * 当前页
	 */
	private int pageNumber;

	/**
	 * 每页大小
	 */
	private int pageSize;

	/**
	 * 参数对象
	 */
	private C params;

	/**
	 * 排序字段
	 */
	private OrderBy orderBy;

	private PageQueryParam() {
	}

	public static <C extends QueryParamDTO> Builder<C> buidler() {
		return new Builder<>();
	}

	/**
	 * 构造器
	 * 
	 * @author Bean
	 *
	 * @param <C>
	 */
	public static class Builder<C extends QueryParamDTO> {
		private Class<C> cls;

		@Getter
		private int pageNumber;

		@Getter
		private int pageSize;

		@Getter
		private C params;

		private OrderBy orderBy;

		private Builder() {
		}

		public Builder<C> paramCls(Class<C> cls) {
			this.cls = cls;
			return this;
		}

		public Builder<C> params(C params) {
			if (params == null) {
				throw new IllegalArgumentException();
			}

			this.params = params;
			return this;
		}

		public <T, R> Builder<C> desc(Property<T, R> property) {
			checkClsExist();
			String fieldName = StringKit.enCodeUnderlined(ClassKit.getFieldName(property));

			if (params == null) {
				throw new IllegalArgumentException();
			}

			if (params.hasOrderProperty(fieldName)) {
				orderBy = new OrderBy(fieldName, EnumOrder.DESC);
				return this;
			} else {
				throw new PropertyNotFoundException(String.format("%s 属性不存在", fieldName));
			}
		}

		public <T, R> Builder<C> asc(Property<T, R> property) {
			checkClsExist();
			String fieldName = ClassKit.getFieldName(property);

			if (params == null) {
				throw new IllegalArgumentException();
			}

			if (params.hasOrderProperty(fieldName)) {
				orderBy = new OrderBy(fieldName, EnumOrder.ASC);
				return this;
			} else {
				throw new PropertyNotFoundException(String.format("%s 属性不存在", fieldName));
			}
		}

		public Builder<C> pageSize(int pageSize) {
			this.pageSize = pageSize;
			return this;
		}

		public Builder<C> pageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
			return this;
		}

		public PageQueryParam<C> build() {
			checkClsExist();

			PageQueryParam<C> pageParam = new PageQueryParam<>();

			// 进行参数的检查
			params.validate();

			pageParam.setParams(this.params);

			pageParam.setPageNumber(this.pageNumber);
			pageParam.setPageSize(this.pageSize);
			pageParam.setOrderBy(this.orderBy);

			return pageParam;
		}
		
		public PageQueryParam<C> build(Class<?> group) {
			checkClsExist();

			PageQueryParam<C> pageParam = new PageQueryParam<>();

			// 进行参数的检查
			params.validate(group);

			pageParam.setParams(this.params);

			pageParam.setPageNumber(this.pageNumber);
			pageParam.setPageSize(this.pageSize);
			pageParam.setOrderBy(this.orderBy);

			return pageParam;
		}

		private void checkClsExist() {
			if (this.cls == null) {
				throw new IllegalArgumentException();
			}
		}
	}

}
