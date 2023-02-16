/**
 * 
 */
package cn.smthit.v4.logger;

import lombok.Getter;

/**
 * @author Bean
 *
 */
public enum EventType {
	/**
	 * 统计
	 */
	EVENT_STATS("stats"),
	/**
	 * 信息
	 */
	EVENT_DESC("info"),

	/**
	 * 访问
	 */
	EVENT_VISIT("access"),
	/**
	 * 审计
	 */
	EVENT_AUDIT("audit"),
	/**
	 * 告警
	 */
	EVENT_ERROR("warn");

	@Getter
	private String value;

	private EventType(String value) {
		this.value = value;
	}

}
