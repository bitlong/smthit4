package cn.smthit.v4.common.lang.enums;


import cn.smthit.v4.common.lang.exception.ServiceException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 枚举值的通用类型
 * 
 * 1.实现value->枚举的转换
 * 2.判断一个枚举 或者value是否在一个枚举的分组中
 * @author Bean
 *
 */
public interface EnumStatus<T> extends IEnumStatus<T> {
	/**
	 * 推荐做法，使用静态方法
	 * @param typeClass
	 * @param value
	 * @return
	 */
	static <T> EnumStatus<T> getEnumStatus(Class<? extends EnumStatus<T>> typeClass, T value) {
		EnumStatus<T> status = EnumStatusKit.getStatusByValue(typeClass, value);
		
		if(status == null) {
			throw new ServiceException("枚举值未定义, typeClass = " + typeClass.getCanonicalName() + ", Value = " + value);
		}
		
		return status;
	}

	/**
	 * 根据状态值判断是否在一个枚举的分组中
	 * @param value
	 * @param group
	 * @return
	 */
	static <T> boolean isInGroupByValue(T value, EnumStatus<T>[] group) {
		for(EnumStatus<T> item : group) {
			if(item.getValue().equals(value))
				return true;
		}
		return false;
	}

	/**
	 * 判断某一个枚举是否在一个分组中
	 * @param status
	 * @param group
	 * @return
	 */
	static <T> boolean isInGroup(EnumStatus<T> status, EnumStatus<T>[] group) {
		if(status == null) {
			return false;
		}
		
		for(EnumStatus<T> item : group) {
			if(item == status) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 获取一个枚举分组下所有的枚举值的集合
	 * @param group
	 * @return
	 */
	public static <T> Set<T> getValueInGroup(EnumStatus<T>[] group) {
		if(group == null || group.length == 0)
			return Collections.emptySet();
		
		Set<T> values = new HashSet<T>();
		for(EnumStatus<T> item : group) {
			values.add(item.getValue());
		}
		
		return values;
	}
}
