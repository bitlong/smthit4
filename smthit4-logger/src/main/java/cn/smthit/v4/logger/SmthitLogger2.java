/**
 * 
 */
package cn.smthit.v4.logger;


import cn.hutool.log.LogFactory;
import cn.smthit.v4.common.lang.kits.GsonKit;

/**
 * @author Bean
 * 
 */
public class SmthitLogger2 {

	public static void debug(String logName, EventData data) {
		String content = GsonKit.toJson(data);
		LogFactory.get(logName).debug(content);
	}
	
	public static void debug(String logName, EventData data, Throwable throwable) {
		String content = GsonKit.toJson(data);
		LogFactory.get(logName).debug(content, throwable);
	}

	public static void info(String logName, EventData data) {
		String content = GsonKit.toJson(data);
		LogFactory.get(logName).info(content);
	}

	public static void info(String logName, EventData data, Throwable throwable) {
		String content = GsonKit.toJson(data);
		LogFactory.get(logName).info(content, throwable);
	}

	public static void warn(String logName, EventData data) {
		String content = GsonKit.toJson(data);
		LogFactory.get(logName).warn(content);
	}

	public static void warn(String logName, EventData data, Throwable throwable) {
		String content = GsonKit.toJson(data);
		LogFactory.get(logName).warn(content, throwable);
	}

	public static void error(String logName, EventData data) {
		String content = GsonKit.toJson(data);
		LogFactory.get(logName).error(content);
	}

	public static void error(String logName, EventData data, Throwable throwable) {
		String content = GsonKit.toJson(data);
		LogFactory.get(logName).error(content, throwable);
	}

	public static void fatal(String logName, EventData data, Throwable throwable) {
		String content = GsonKit.toJson(data);
		LogFactory.get(logName).error(content, throwable);
	}
}
