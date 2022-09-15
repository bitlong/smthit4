/**
 * 
 */
package cn.smthit.v4.common.lang.kits;

import cn.smthit.v4.common.lang.exception.DataParseException;
import cn.smthit.v4.common.lang.exception.ServiceException;

/**
 * @author Bean
 *
 */
public class FloatKit {

	/**
	 *
	 */
	public FloatKit() {
	}
	
	public static Float parseFloat(Object value, Float defaultValue) {
		if(value == null) {
			return defaultValue;
		}
		
		if(value instanceof Float) {
			return (Float)value;
		}
		
		if(value.getClass().isAssignableFrom(Number.class)) {
			return ((Number)value).floatValue();
		}
		
		try {
			return Float.parseFloat(value.toString());
		}catch(NumberFormatException exp) {
			throw new DataParseException(exp.getMessage());
		}
	}
	
	public static Float parseFloat(Object value, Float defaultValue, String tips) {
		try {
			return parseFloat(value, defaultValue);
		} catch(DataParseException exp) {
			throw new ServiceException(tips);
		}
	}
}
