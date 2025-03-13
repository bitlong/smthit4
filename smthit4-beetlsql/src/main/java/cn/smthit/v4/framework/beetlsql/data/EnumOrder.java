/**
 *
 */
package cn.smthit.v4.framework.beetlsql.data;

import lombok.Getter;

/**
 * @author Bean
 *
 */
@Getter
public enum EnumOrder {
	ASC("asc", "升序"),
	DESC("desc", "降序");

	private final String value;
	private final String desc;

	private EnumOrder(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

}
