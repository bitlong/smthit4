/**
 * 
 */
package cn.smthit.v4.common.lang.data;

import java.io.Serializable;

/**
 * @author Bean
 *
 */
public interface IParamDTO extends Serializable {

	public void validate(Class<?> group);

	public void validate();
}
