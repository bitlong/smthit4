/**
 * 
 */
package cn.smthit.v4.logger;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bean
 * 日志的内容
 */
@Builder
@Data
public class EventData {
	/**
	 * 事件名称
	 */
	private String eventName;
	
	/**
	 * 事件类型
	 */
	private EventType eventType;
	
	/**
	 * 事件内容
	 */
	private Map<String, Object> content; 	
	
	public EventData putContent(String key, Object value) {
		if(content == null) {
			content = new HashMap<String, Object>();
		}
		
		content.put(key, value);
		
		return this;
	}
}
