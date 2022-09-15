/**
 * 
 */
package com.google.common.eventbus;

import java.util.concurrent.Executor;

/**
 * @author Bean
 * 异步队列执行器
 */
public class SmthitAsyncEventBus extends EventBus {

	public SmthitAsyncEventBus(String identifier, Executor executor, SubscriberExceptionHandler subscriberExceptionHandler) {
		super(identifier, executor, Dispatcher.legacyAsync(), subscriberExceptionHandler);
	}

}
