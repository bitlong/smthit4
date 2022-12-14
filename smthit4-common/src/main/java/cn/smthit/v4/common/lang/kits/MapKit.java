/**
 * 
 */
package cn.smthit.v4.common.lang.kits;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author Bean
 * Set和Map工具类
 */
public final class MapKit {

	private MapKit() {
	}

	/**
	 * 将一个集合转成Map，指定指定属性作为主键，value为对象本身
	 * @param values
	 * @param key
	 * @return
	 */
	public static <V, T, R> Map<R, V> toMap(Collection<V> values, Property<T, R> key) {
		//TODO
		return Collections.emptyMap();
	}
	
	/**
	 * 将一个集合转成Map，指定的属性为主键，value为指定的属性
	 * @param values
	 * @param key
	 * @param value
	 * @return
	 */
	public static <V, T, R, T1, R1> Map<R, V> toMap(Collection<V> values, Property<T, R> key, Property<T1, R1> value) {
		//TODO
		return Collections.emptyMap();
	}
	
	/**
	 * 将一个集合的某个属性转换成集合
	 * @param values
	 * @param key
	 * @return
	 */
	public static <V, T, R> Set<R> toSet(Collection<V> values, Property<T, R> key) {
		//TODO
		return Collections.emptySet();
	}
	
	/**
	 * 移除map中null key或者value空值
	 * 
	 * @param map
	 */
	public static void removeNullEntry(Map<?, ?> map) {
		removeNullKey(map);
		removeNullValue(map);
	}

	public static void removeEmptyEntry(Map<?, ?> map) {
		removeEmptyKey(map);
		removeEmptyValue(map);
	}

	/**
	 * 移除map的 null key
	 * 
	 * @param map
	 * @return
	 */
	public static void removeNullKey(Map<?, ?> map) {
		Set<?> set = map.keySet();
		for (Iterator<?> iterator = set.iterator(); iterator.hasNext();) {
			Object obj = (Object) iterator.next();
			removeNull(obj, iterator);
		}
	}

	public static void removeEmptyKey(Map<?, ?> map) {
		Set<?> set = map.keySet();
		for (Iterator<?> iterator = set.iterator(); iterator.hasNext();) {
			Object obj = (Object) iterator.next();
			removeEmpty(obj, iterator);
		}
	}

	/**
	 * 移除map中的 null value空值
	 * 
	 * @param map
	 * @return
	 */
	public static void removeNullValue(Map<?, ?> map) {
		Set<?> set = map.keySet();
		for (Iterator<?> iterator = set.iterator(); iterator.hasNext();) {
			Object obj = (Object) iterator.next();
			Object value = (Object) map.get(obj);
			removeNull(value, iterator);
		}
	}

	public static void removeEmptyValue(Map<?, ?> map) {
		Set<?> set = map.keySet();
		for (Iterator<?> iterator = set.iterator(); iterator.hasNext();) {
			Object obj = (Object) iterator.next();
			Object value = (Object) map.get(obj);
			removeEmpty(value, iterator);
		}
	}

	/**
	 * Iterator 是工作在一个独立的线程中，并且拥有一个 mutex 锁。 Iterator
	 * 被创建之后会建立一个指向原来对象的单链索引表，当原来的对象数量发生变化时，这个索引表的内容不会同步改变，
	 * 所以当索引指针往后移动的时候就找不到要迭代的对象，所以按照 fail-fast 原则 Iterator 会马上抛出
	 * java.util.ConcurrentModificationException 异常。 所以 Iterator
	 * 在工作的时候是不允许被迭代的对象被改变的。 但你可以使用 Iterator 本身的方法 remove() 来删除对象， Iterator.remove()
	 * 方法会在删除当前迭代对象的同时维护索引的一致性。
	 * 
	 * @param obj
	 * @param iterator
	 */
	private static void removeNull(Object obj, Iterator<?> iterator) {
		if (obj instanceof Collection) {
			Collection<?> col = (Collection<?>) obj;
			if (col == null || col.isEmpty()) {
				iterator.remove();
			}

		} else if (obj instanceof Map) {
			Map<?, ?> temp = (Map<?, ?>) obj;
			if (temp == null || temp.isEmpty()) {
				iterator.remove();
			}

		} else if (obj instanceof Object[]) {
			Object[] array = (Object[]) obj;
			if (array == null || array.length <= 0) {
				iterator.remove();
			}
		} else {
			if (obj == null) {
				iterator.remove();
			}
		}
	}

	private static void removeEmpty(Object obj, Iterator<?> iterator) {
		if (obj instanceof String) {
			String str = (String) obj;
			if (StringUtils.isBlank(str)) {
				iterator.remove();
			}
		} else if (obj instanceof Collection) {
			Collection<?> col = (Collection<?>) obj;
			if (col == null || col.isEmpty()) {
				iterator.remove();
			}

		} else if (obj instanceof Map) {
			Map<?, ?> temp = (Map<?, ?>) obj;
			if (temp == null || temp.isEmpty()) {
				iterator.remove();
			}

		} else if (obj instanceof Object[]) {
			Object[] array = (Object[]) obj;
			if (array == null || array.length <= 0) {
				iterator.remove();
			}
		} else {
			if (obj == null) {
				iterator.remove();
			}
		}
	}
}
