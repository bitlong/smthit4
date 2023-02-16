/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.data2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Bean
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SampleDTO extends QueryParamDTO {
	@NotEmpty
	private Long id;

	public SampleDTO() {
		super();
		// 增加可排序的字段
		supportedOrderField(SampleDTO::getId);
	}

}
