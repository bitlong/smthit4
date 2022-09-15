/**
 * 
 */
package cn.smthit.v4.common.lang.enums;


import cn.smthit.v4.common.lang.exception.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bean
 *
 */
public final class EnumStatusKit {

	@SuppressWarnings("unchecked")
	public static <T> EnumStatus<T> getStatusByValue(Class<? extends EnumStatus<T>> enums, T value) {
		if (enums.isEnum()) {
			Object[] statuses = enums.getEnumConstants();
			for (int i = 0; i < statuses.length; i++) {
				EnumStatus<T> status = (EnumStatus<T>) statuses[i];
				if (status.getValue().equals(value)) {
					return status;
				}
			}
		}

		throw new ServiceException("枚举状态不存在, value = " + value);
	}

	@SuppressWarnings("unchecked")
	public static <T> EnumStatus<T> getStatusByDesc(Class<? extends EnumStatus<T>> enums, String desc) {
		if (enums.isEnum()) {
			Object[] statuses = enums.getEnumConstants();
			for (int i = 0; i < statuses.length; i++) {
				EnumStatus<T> status = (EnumStatus<T>) statuses[i];
				if (status.getDesc().equals(desc)) {
					return status;
				}
			}
		}

		throw new ServiceException("枚举状态不存在, desc = " + desc);
	}

	/**
	 * 
	 * @param enums 枚举类
	 * @param sta   提示返回值
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<Map<String, String>> getListByEnum(Class<?> enums, String sta) {
		List<Map<String, String>> resList = new ArrayList<Map<String, String>>();

		Map<String, String> allTab = new HashMap<>();

		allTab.put(sta + "_value", "-1");
		allTab.put(sta + "_name", "全部");

		resList.add(0, allTab);

		if (enums.isEnum()) {
			Object[] statuses = enums.getEnumConstants();
			for (int i = 0; i < statuses.length; i++) {
				EnumStatus status = (EnumStatus<T>) statuses[i];
				Map<String, String> tab = new HashMap<>();
				tab.put(sta + "_value", status.getValue() + "");
				tab.put(sta + "_name", status.getDesc());

				resList.add((i + 1), tab);
			}
		}

		return resList;
	}

	/**
	 * 数据列表
	 * 
	 * @param enums 枚举类
	 * @param sta   提示返回值
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> List<Map<String, String>> getDataListByEnum(Class<? extends EnumStatus<T>> enums, String sta) {
		List<Map<String, String>> resList = new ArrayList<Map<String, String>>();

		if (enums.isEnum()) {
			Object[] statuses = enums.getEnumConstants();
			for (int i = 0; i < statuses.length; i++) {
				EnumStatus<T> status = (EnumStatus<T>) statuses[i];

				Map<String, String> tab = new HashMap<>();
				tab.put(sta + "_value", status.getValue() + "");
				tab.put(sta + "_name", status.getDesc());

				resList.add((i), tab);
			}
		}
		return resList;
	}
}
