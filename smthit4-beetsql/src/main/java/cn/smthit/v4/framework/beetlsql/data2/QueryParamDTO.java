/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.data2;



import cn.smthit.v4.common.lang.kits.ClassKit;
import cn.smthit.v4.common.lang.kits.Property;
import cn.smthit.v4.common.lang.kits.StringKit;
import cn.smthit.v4.framework.beetlsql.data.EnumOrder;
import cn.smthit.v4.framework.beetlsql.data.OrderBy;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Bean
 *
 */
public class QueryParamDTO extends ParamDTO {
	private PageQueryParam<?> pageParam;
	
	protected OrderBy defaultOrderBy = new OrderBy("id", EnumOrder.DESC);
	protected Set<String> orders = new HashSet<>();

	public QueryParamDTO() {
		orders.add("id");
	}
	
	public  <T, R>  void supportedOrderField(Property<T, R> property) {
		String fieldName = ClassKit.getFieldName(property);
		orders.add(StringKit.enCodeUnderlined(fieldName));
	}
	
	public OrderBy orderBy() {
		if(pageParam != null && pageParam.getOrderBy() != null) {
			return pageParam.getOrderBy();
		}
		
		return defaultOrderBy;
	}
	
	public boolean hasOrderProperty(String fieldName) {
		if(orders.contains(fieldName))
			return true;
		
		return false;
	}
}
