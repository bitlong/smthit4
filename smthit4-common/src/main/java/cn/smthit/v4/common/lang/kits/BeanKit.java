/**
 * 
 */
package cn.smthit.v4.common.lang.kits;

import cn.smthit.v4.common.lang.enums.EnumIgnoreValue;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bean
 *
 */
@Slf4j
public class BeanKit {

	/** 正则表达式 用于匹配属性的第一个字母 [a-zA-Z]} **/
	private static final String REGEX = "[a-zA-Z]";

	/**
	 * 设置对象的某个属性值
	 * 
	 * @param obj
	 * @param attribute
	 * @param value
	 */
	public static void setAttrributeValue(Object obj, String attribute, Object value) {

		String method_name = convertToMethodName(attribute, obj.getClass(), true);

		Method[] methods = obj.getClass().getMethods();

		for (Method method : methods) {
			/**
			 * 因为这里只是调用bean中属性的set方法，属性名称不能重复 所以set方法也不会重复，所以就直接用方法名称去锁定一个方法
			 * （注：在java中，锁定一个方法的条件是方法名及参数）
			 * **/
			if (method.getName().equals(method_name)) {
				Class<?>[] parameterC = method.getParameterTypes();
				try {
					/**
					 * 如果是基本数据类型时（如int、float、double、byte、char、boolean）
					 * 需要先将Object转换成相应的封装类之后再转换成对应的基本数据类型 否则会报
					 * ClassCastException
					 **/
					if (parameterC[0] == int.class) {
						method.invoke(obj, ((Integer) value).intValue());
						break;
					} else if (parameterC[0] == float.class) {
						method.invoke(obj, ((Float) value).floatValue());
						break;
					} else if (parameterC[0] == double.class) {
						method.invoke(obj, ((Double) value).doubleValue());
						break;
					} else if (parameterC[0] == byte.class) {
						method.invoke(obj, ((Byte) value).byteValue());
						break;
					} else if (parameterC[0] == char.class) {
						method.invoke(obj, ((Character) value).charValue());
						break;
					} else if (parameterC[0] == boolean.class) {
						method.invoke(obj, ((Boolean) value).booleanValue());
						break;
					} else if (parameterC[0] == long.class) {
						method.invoke(obj, ((Long) value).longValue());
						break;
					} else {
						method.invoke(obj, parameterC[0].cast(value));
						break;
					}
				} catch (Exception exp) {
					log.warn(exp.getMessage(), exp);
				}
			}
		}
	}

	/**
	 * 通过属性名获取方法名 getAttribute 或者 setAttribute 或者 isAttribute
	 * 
	 * @param attribute
	 * @param objClass
	 * @param isSet
	 * @return
	 */
	private static String convertToMethodName(String attribute, Class<?> objClass, boolean isSet) {
		/** 通过正则表达式来匹配第一个字符 **/
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(attribute);

		StringBuilder sb = new StringBuilder();

		/** 如果是set方法名称 **/
		if (isSet) {
			sb.append("set");
		} else {
			/** get方法名称 **/
			try {
				Field attributeField = ReflectKit.getFields(objClass, attribute);
				;
				if (attributeField == null) {
					throw new NoSuchFieldException(String.format("%s can not found.", attribute));
				}
				//attributeField.getType() == Boolean.class

				/** 如果类型为boolean **/
				if (attributeField.getType() == boolean.class) {
					sb.append("is");
				} else {
					sb.append("get");
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException exp) {
				exp.printStackTrace();
			}
		}

		/** 针对以下划线开头的属性 **/
		if (attribute.charAt(0) != '_' && m.find()) {
			sb.append(m.replaceFirst(m.group().toUpperCase()));
		} else {
			sb.append(attribute);
		}

		return sb.toString();
	}

	/**
	 * 获取属性对应值
	 * 
	 * @param obj
	 * @param attribute
	 * @return
	 */
	public static Object getAttrributeValue(Object obj, String attribute) {
		Object value = null;

		if (attribute.equalsIgnoreCase("serialVersionUID"))
			return value;

		String methodName = convertToMethodName(attribute, obj.getClass(), false);
		try {
			/** 由于get方法没有参数且唯一，所以直接通过方法名称锁定方法 **/
			Method methods = obj.getClass().getMethod(methodName);
			if (methods != null) {
				value = methods.invoke(obj);
			}
		} catch (SecurityException e) {
			//e.printStackTrace();
		} catch (NoSuchMethodException e) {
			//e.printStackTrace();
		} catch (IllegalArgumentException e) {
			//e.printStackTrace();
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
		} catch (InvocationTargetException e) {
			//e.printStackTrace();
		}
		return value;
	}

	public static Object copyPropertiesFromMap2Bean(Object updateObj, Map<String, Object> params) {
		Field[] fields = updateObj.getClass().getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			Field fld = fields[i];
			Object obj = params.get(fld.getName());
			if (obj != null) {
				BeanKit.setAttrributeValue(updateObj, fld.getName(), obj);
			} 
			
			if(obj == null) {
				//TODO underscore
				String fieldName = StringKit.separateCamelCase(fld.getName(), "_");
				obj = params.get(fieldName);
				if(obj != null) {
					BeanKit.setAttrributeValue(updateObj, fld.getName(), obj);
				}
			}
		}
		return updateObj;
	}

	public static Object copyPropertiesFromMap2Bean(Object updateObj, Map<String, Object> params, String[] excludes) {
		Field[] fields = updateObj.getClass().getDeclaredFields();

		Set<String> mapExcludes = new HashSet<String>();
		if (excludes != null && excludes.length > 0) {
			for (String exclude : excludes) {
				mapExcludes.add(exclude);
			}
		}

		for (int i = 0; i < fields.length; i++) {
			Field fld = fields[i];
			Object obj = params.get(fld.getName());
			if (obj != null && !mapExcludes.contains(fld.getName()) && fld.getType().equals(obj.getClass())) {
				BeanKit.setAttrributeValue(updateObj, fld.getName(), obj);
			}
			
			if(obj == null) {
				//TODO underscore
				obj = params.get(StringKit.separateCamelCase(fld.getName(), "_"));
				if(obj != null) {
					BeanKit.setAttrributeValue(updateObj, fld.getName(), obj);
				}
			}
		}
		return updateObj;
	}
	
	public static Map<String, Object> copyPropertiesFromBean2Map(Object sourceObj, Map<String, Object> params) {
		return copyPropertiesFromBean2Map(sourceObj, params, new String[] {}, EnumIgnoreValue.NONE);
	}

	public static Map<String, Object> copyPropertiesFromBean2Map(Object sourceObj, Map<String, Object> params, EnumIgnoreValue ignoreValue) {
		return copyPropertiesFromBean2Map(sourceObj, params, new String[] {}, ignoreValue);
	}

	public static Map<String, Object> copyPropertiesFromBean2Map(Object sourceObj, Map<String, Object> params, String[] excludes) {
		return copyPropertiesFromBean2Map(sourceObj, params, excludes, EnumIgnoreValue.NONE);
	}
	
	public static Map<String, Object> copyPropertiesFromBean2Map(Object sourceObj, Map<String, Object> params, String[] excludes, EnumIgnoreValue ignoreValue) {
		Field[] fields = sourceObj.getClass().getDeclaredFields();

		Set<String> mapExcludes = new HashSet<String>();
		if (excludes != null && excludes.length > 0) {
			for (String exclude : excludes) {
				mapExcludes.add(exclude);
			}
		}

		if (params == null) {
			params = new HashMap<String, Object>();
		}

		for (Field fld : fields) {
			Object value = BeanKit.getAttrributeValue(sourceObj, fld.getName());
			if (value != null && !mapExcludes.contains(fld.getName())) {
				params.put(fld.getName(), value);
			}
		}
		
		if(ignoreValue == EnumIgnoreValue.EMPTY) {
			MapKit.removeEmptyValue(params);
		} else if(ignoreValue == EnumIgnoreValue.NULL) {
			MapKit.removeNullEntry(params);
		} else {
			//do nothing.
		}

		return params;
	}

	public static <T> List<Map<String, Object>> copyPropertiesFromBean2Map(List<T> sourceList) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		if (sourceList != null) {
			for (Object source : sourceList) {
				Map<String, Object> param = new HashMap<>(10);
				Map<String, Object> destMap = copyPropertiesFromBean2Map(source, param, new String[] {}, EnumIgnoreValue.NONE);
				result.add(destMap);
			}
		}

		return result;
	}

	public static void convert(Object sourceObject, Object destObject) {
		BeanKit.copyPropertiesFromBean2Bean(sourceObject, destObject);
	}

	/**
	 * 复制两个类中 属性名相同且类型相同的 对象值。
	 * @deprecated  use copy()
	 * @param sourceObj
	 * @param destObj
	 */
	public static void copyPropertiesFromBean2Bean(Object sourceObj, Object destObj) {
		if (sourceObj == null || destObj == null)
			return;

		Field[] fields = ReflectKit.getAllField(sourceObj.getClass());
		Field[] destFields = ReflectKit.getAllField(destObj.getClass());

		Map<String, Object> mapDestFields = new HashMap<String, Object>();

		for (int i = 0; i < destFields.length; i++) {
			mapDestFields.put(destFields[i].getName(), destFields[i]);
		}

		for (int i = 0; i < fields.length; i++) {
			Field fld = fields[i];

			Field destField = (Field) mapDestFields.get(fld.getName());

			if (destField != null && destField.getType().equals(fld.getType())) {
				Object value = BeanKit.getAttrributeValue(sourceObj, fld.getName());
				BeanKit.setAttrributeValue(destObj, destField.getName(), value);
			}
		}
	}

	public static void convert(Object sourceObject, Object destObject, String[] excludes) {
		BeanKit.copyPropertiesFromBean2Bean(sourceObject, destObject, excludes);
	}

	public static void copyPropertiesFromBean2Bean(Object sourceObj, Object destObj, String[] excludes) {
		Set<String> mapExcludes = new HashSet<String>();
		if (excludes != null && excludes.length > 0) {
			for (String exclude : excludes) {
				mapExcludes.add(exclude);
			}
		}

		if (sourceObj == null || destObj == null)
			return;

		Field[] fields = ReflectKit.getAllField(sourceObj.getClass());
		Field[] destFields = ReflectKit.getAllField(destObj.getClass());

		Map<String, Object> mapDestFields = new HashMap<String, Object>();

		for (int i = 0; i < destFields.length; i++) {
			if(mapExcludes.contains(destFields[i].getName())) {
				continue;
			}
			mapDestFields.put(destFields[i].getName(), destFields[i]);
		}

		for (int i = 0; i < fields.length; i++) {
			Field fld = fields[i];
			Field destField = (Field) mapDestFields.get(fld.getName());

			if (destField != null && destField.getType().equals(fld.getType())) {
				Object value = BeanKit.getAttrributeValue(sourceObj, fld.getName());
				BeanKit.setAttrributeValue(destObj, destField.getName(), value);
			}
		}
	}

	public static void convert(Object sourceObject, Object destObject, IPropertyChanged changedCallback) {
		BeanKit.copyPropertiesFromBean2Bean(sourceObject, destObject, changedCallback);
	}

	public static boolean copyPropertiesFromBean2Bean(Object sourceObj, Object destObj, IPropertyChanged changedCallback) {
		if (sourceObj == null || destObj == null)
			return false;

		boolean hasChanged = false;
		
		Field[] fields = ReflectKit.getAllField(sourceObj.getClass());
		Field[] destFields = ReflectKit.getAllField(destObj.getClass());

		Map<String, Object> mapDestFields = new HashMap<String, Object>();

		for (int i = 0; i < destFields.length; i++) {
			mapDestFields.put(destFields[i].getName(), destFields[i]);
		}

		for (int i = 0; i < fields.length; i++) {
			Field fld = fields[i];

			Field destField = (Field) mapDestFields.get(fld.getName());

			if (destField != null && destField.getType().equals(fld.getType())) {
				Object value = BeanKit.getAttrributeValue(sourceObj, fld.getName());
				Object descValue = BeanKit.getAttrributeValue(destObj, destField.getName());
				if ((value != null && descValue != null && !value.equals(descValue)) 
						|| (value == null && descValue != null) 
						|| (value != null && descValue == null)) {
					if (changedCallback != null) {
						changedCallback.onPropertyChanged(destField.getName(), descValue, value);
					}
					BeanKit.setAttrributeValue(destObj, destField.getName(), value);
					hasChanged = true;
				}
			}
		}
		
		return hasChanged;
	};


}
