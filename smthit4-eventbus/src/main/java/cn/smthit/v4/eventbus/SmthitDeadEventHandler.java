/**
 * 
 */
package cn.smthit.v4.eventbus;

import com.google.common.eventbus.DeadEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bean
 *
 */
@Slf4j
public class SmthitDeadEventHandler implements IDeadEventHandler {

	@Override
	public void handle(DeadEvent deadEvent) {
		deadEvent.getEvent();
		log.info("DeadEvent Handle :" + message(deadEvent.getSource(), deadEvent.getEvent()));
	}

	private static String message(Object source, Object event) {
		return "DeadEvent source from  " + source.toString()  + " Event Object : " + event.toString();
	}
}
