/**
 * 
 */
package cn.smthit.v4.eventbus;

import com.google.common.eventbus.DeadEvent;

/**
 * @author Bean
 *
 */
public interface IDeadEventHandler {
	public void handle(DeadEvent deadEvent);
}
