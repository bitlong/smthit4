/**
 * 
 */
package cn.smthit.v4.eventbus.mq;

import cn.smthit.v4.eventbus.mq.spi.IMQService;
import com.google.common.base.Preconditions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bean
 * MQFactory MQ服务工厂类
 */
public class MQFactory {
	private static Map<String, IMQService> mqServiceMap;
	
	public MQFactory() {
		mqServiceMap = new ConcurrentHashMap<>();
	}
	
	public void lookupMQService(String key) {
		mqServiceMap.get(key);
	}
	
	public void registMQService(String key, IMQService mqService) {
		Preconditions.checkNotNull(mqService);
		Preconditions.checkNotNull(key);
		
		if(!mqServiceMap.containsKey(key)) {
			mqServiceMap.put(key, mqService);
		}
	}
}
