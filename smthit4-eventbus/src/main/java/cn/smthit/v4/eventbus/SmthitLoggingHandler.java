/**
 * 
 */
package cn.smthit.v4.eventbus;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author Bean
 *
 */
public class SmthitLoggingHandler implements SubscriberExceptionHandler {
	static final SmthitLoggingHandler INSTANCE = new SmthitLoggingHandler();

	@Override
	public void handleException(Throwable exception, SubscriberExceptionContext context) {
		Logger logger = logger(context);
		logger.error(message(context), exception);
	}

	private static Logger logger(SubscriberExceptionContext context) {
		return LoggerFactory.getLogger(context.getEventBus().getClass().getName() + "." + context.getEventBus().identifier());
	}

	private static String message(SubscriberExceptionContext context) {
		Method method = context.getSubscriberMethod();
		return "Exception thrown by subscriber method " + 
				method.getName() + 
				'(' + 
				method.getParameterTypes()[0].getName() + 
				')' + 
				" on subscriber " + context.getSubscriber() + 
				" when dispatching event: " + context.getEvent();
	}
}
