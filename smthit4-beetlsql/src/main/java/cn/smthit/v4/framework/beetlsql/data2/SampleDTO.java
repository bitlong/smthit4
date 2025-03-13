/**
 *
 */
package cn.smthit.v4.framework.beetlsql.data2;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Bean
 *
 */
@Deprecated
@Data
@EqualsAndHashCode(callSuper = true)
public class SampleDTO extends QueryParamDTO {
	@NotNull
	private Long id;

	public SampleDTO() {
		super();
		// 增加可排序的字段
		supportedOrderField(SampleDTO::getId);
	}

}
