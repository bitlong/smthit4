/**
 * 
 */
package cn.smthit.v4.common.lang.kits;

import cn.smthit.v4.common.lang.exception.DataParseException;
import cn.smthit.v4.common.lang.exception.ServiceException;
import org.apache.commons.lang.StringUtils;

/**
 * @author Bean
 *
 */
public class DoubleKit {

	/**
	 *
	 */
	public DoubleKit() {
	}

	public static Double parseDouble(Object value, Double defaultValue) {
		if(value == null || StringUtils.isBlank(value.toString())) {
			return defaultValue;
		}
		
		if(value instanceof Double) {
			return (Double)value;
		}
		
		if(value.getClass().isAssignableFrom(Number.class)) {
			return ((Number)value).doubleValue();
		}
		
		try {
			return Double.parseDouble(value.toString());
		}catch(NumberFormatException exp) {
			throw new DataParseException(exp.getMessage());
		}
	}
	
	public static Double parseDouble(Object value, Double defaultValue, String tips) {
		try {
			return parseDouble(value, defaultValue);
		} catch(DataParseException exp) {
			throw new ServiceException(tips);
		}
	}
}
