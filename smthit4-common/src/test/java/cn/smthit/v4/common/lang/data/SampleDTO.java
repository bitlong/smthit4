/**
 * 
 */
package cn.smthit.v4.common.lang.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author Bean
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SampleDTO extends PageParamDTO {
	@NotNull
	private Long id;

	public SampleDTO() {
		super();
		// 增加可排序的字段
	}
}
