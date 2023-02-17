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
public enum EnumGenderStatus implements EnumStatus<Integer> {
	MALE(1, "男"),
	FEMALE(2, "女"),
	UNKOWN(3, "待定");
	
	private Integer value;
	private String desc;
}
