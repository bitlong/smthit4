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
public class IntegerKit {

	/**
	 *
	 */
	public IntegerKit() {
	}

	public static Integer parseInteger(Object value, Integer defaultValue) {
		if(value == null || StringUtils.isBlank(value.toString())) {
			return defaultValue;
		}
		
		if(value instanceof Integer) {
			return (Integer)value;
		}
		
		if(value.getClass().isAssignableFrom(Number.class)) {
			return ((Number)value).intValue();
		}
		
		try {
			return Integer.parseInt(value.toString());
		} catch(Exception exp) {
			throw new DataParseException("数据类型转换失败, value = " + value);
		}		
	}
	
	public static Integer parseInteger(Object value, Integer defaultValue, String tips) {
		try {
			return parseInteger(value, defaultValue);
		} catch(DataParseException exp) {
			throw new ServiceException(tips);
		}
	}
}
