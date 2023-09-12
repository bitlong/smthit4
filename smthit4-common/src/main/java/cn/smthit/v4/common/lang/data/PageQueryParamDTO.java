/**
 * 
 */
package cn.smthit.v4.common.lang.data;


import lombok.Data;

/**
 * @author Bean 翻页查询的参数对象
 */
@Data
@lombok.experimental.Accessors(chain = true)
public class PageQueryParamDTO<T> extends  PageParamDTO{

	/**
	 * 参数对象
	 */
	private T params;
}
