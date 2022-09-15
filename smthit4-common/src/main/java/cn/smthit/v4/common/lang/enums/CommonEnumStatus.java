/**
 * 
 */
package cn.smthit.v4.common.lang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bean
 *
 */
@Getter
@AllArgsConstructor
public enum CommonEnumStatus implements EnumStatus<Integer> {
	UNKOWN(-10000, "未知状态");

	private Integer value;
	private String desc;
}
