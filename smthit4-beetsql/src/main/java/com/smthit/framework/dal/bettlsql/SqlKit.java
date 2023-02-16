/**
 * 
 */
package com.smthit.framework.dal.bettlsql;

import java.util.HashMap;
import java.util.Map;

import cn.smthit.v4.common.lang.exception.ObjectNotFoundException;
import cn.smthit.v4.common.lang.exception.ServiceException;
import org.beetl.sql.core.SQLManager;


/**
 * @author Bean
 *
 */
/**
 * @author Bean
 *s
 */
public class SqlKit {
	private static SQLManager $;
	
	private static Map<String, SQLManager> mapSM = new HashMap<String, SQLManager>();

	private SqlKit() {
	}

	/**
	 * 获取默认的主数据源，如果是单数据源就是单数据源对应的SQLManager
	 * @return
	 */
	public static SQLManager $() {
		return $;
	}

	public static void $(SQLManager sqlManager) {
		$ = sqlManager;
	}
	
	public static void $(String dsName, SQLManager sqlManager) {
		if(!mapSM.containsKey(dsName)) {
			mapSM.put(dsName, sqlManager);
		} else {
			throw new ServiceException("数据源 " + dsName + " 已经存在.");
		}
	}

	public static <T> T mapper(Class<T> mapperInterface) {
		return $().getMapper(mapperInterface);
	}

	public static <T> T mapper(String dsName, Class<T> mapperInterface) {
		return mapper(dsName, mapperInterface, true);
	}
	
	/**
	 * 
	 * @param <T>
	 * @param dsName 数据源
	 * @param mapperInterface 需要包装的接口
	 * @param useDefault
	 * false - 如果数据源不存在，则报异常
	 * true  - 如果数据源不存在，则使用默认数据源
	 * @return
	 */
	public static <T> T mapper(String dsName, Class<T> mapperInterface, boolean useDefault) {
		
		SQLManager currentSQLManager = mapSM.get(dsName);
		
		if(useDefault == true) {
			//不存在则使用默认的数据源
			if(currentSQLManager == null) {
				currentSQLManager = $;
			}
		} else {
			if(currentSQLManager == null) {
				throw new ObjectNotFoundException("数据源 " + dsName + "所对应的 SQLManager不存在");
			}
		}
		
		return currentSQLManager.getMapper(mapperInterface);
	}
	
	public static SQLManager $(String dsName) {
		if(mapSM.containsKey(dsName)) {
			return mapSM.get(dsName);
		} else {
			throw new ObjectNotFoundException("数据源 " + dsName + "所对应的 SQLManager不存在");
		}
	}	
	
	public static void addSQLManager(String dsName, SQLManager sqlManager) {
		mapSM.put(dsName, sqlManager);
	}
}