/**
 * 
 */
package com.google.common.eventbus;

import com.google.common.util.concurrent.MoreExecutors;

/**
 * @author Bean
 *
 */
public class SyncEventBus extends EventBus {
	  public SyncEventBus(String identifier, SubscriberExceptionHandler handler) {
		  super(identifier, MoreExecutors.directExecutor(), Dispatcher.perThreadDispatchQueue(), handler);
	  }
}