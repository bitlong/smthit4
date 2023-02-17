/**
 * 
 */
package cn.smthit.v4.common.lang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bean
 * @since 1.0.5
 */
@Getter
@AllArgsConstructor
public enum EnumLogicStatus implements EnumStatus<Integer> {
	NORMAL(1, "正常"),
	DELETED(0, "删除"),
	TRUE(1, "真"),
	FALSE(0, "假"),
	YES(1, "是"),
	NO(0, "否")
	;
	
	private Integer value;
	private String desc;
}
