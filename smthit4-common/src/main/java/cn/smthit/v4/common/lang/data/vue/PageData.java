/**
 * 
 */
package cn.smthit.v4.common.lang.data.vue;

import lombok.Data;
import lombok.ToString;

/**
 * @author dinghq VUE分页返回数据的默认格式
 */
@Data
@ToString
public class PageData {
	private long count;
	private Object data;

	public PageData(long count, Object data) {
		this.count = count;
		this.data = data;
	}
}
