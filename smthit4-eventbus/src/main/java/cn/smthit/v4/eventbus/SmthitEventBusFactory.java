/**
 * 
 */
package cn.smthit.v4.eventbus;

import cn.smthit.v4.common.lang.exception.ServiceException;
import com.google.common.eventbus.*;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Bean
 * 
 */
public class SmthitEventBusFactory {
	private final String SMTHIT_DEFAULT_SYNC_BUS =  "SMTHIT_DEFAULT_SYNC_BUS";
	private final String SMTHIT_DEFAULT_ASYNC_BUS =  "SMTHIT-DEFAULT-ASYNC-BUS";
	
	private EventBus defaultSyncEventBus;
	private EventBus defaultAsyncEventBus;
	
	private ExecutorService asyncThreadPoolExecutor; 
	private SubscriberExceptionHandler exceptionHandler = SmthitLoggingHandler.INSTANCE;
	
	@Setter
	private IDeadEventHandler deadEventHandler = new SmthitDeadEventHandler();
	@Setter
	private int asycThreadPoolSize = 10;
	
	public void init() {
		asyncThreadPoolExecutor = Executors.newFixedThreadPool(asycThreadPoolSize);
		
		defaultSyncEventBus = new SyncEventBus(SMTHIT_DEFAULT_SYNC_BUS, exceptionHandler);
		defaultAsyncEventBus = new SmthitAsyncEventBus(SMTHIT_DEFAULT_ASYNC_BUS, asyncThreadPoolExecutor, exceptionHandler);
	
		defaultSyncEventBus.register(this);
		defaultAsyncEventBus.register(this);
	}
	
    @Subscribe
	public void listen(DeadEvent deadEvent) {
    	if(deadEventHandler != null) {
    		deadEventHandler.handle(deadEvent);
    	}
	}
	
	public void destroy() {
		defaultSyncEventBus.unregister(this);
		defaultAsyncEventBus.unregister(this);
	}
	
	/**
	 * 注册消息总线
	 * @param listener
	 */
	public void registerSyncEventBus(Object listener) {
		defaultSyncEventBus.register(listener);
	}
	
	public void registerAsyncEventBus(Object listener) {
		defaultAsyncEventBus.register(listener);
	}

	/**
	 * 注册指定的消息总线
	 * @param eventBusName
	 * @param listener
	 */
	public void registerEventBus(String eventBusName, Object listener) {
		if(SMTHIT_DEFAULT_SYNC_BUS.equalsIgnoreCase(eventBusName)) {
			defaultSyncEventBus.register(listener);
			return;
		} 
		
		if(SMTHIT_DEFAULT_ASYNC_BUS.equalsIgnoreCase(eventBusName)) {
			defaultAsyncEventBus.register(this);
			return;
		}
		
		throw new ServiceException(MessageFormat.format("{0}消息中线不存在", eventBusName));
	}
	
	/**
	 * 从默认总线上下线
	 * @param listener
	 */
	public void unregisterSyncEventBus(Object listener) {
		defaultSyncEventBus.unregister(listener);
	}
	
	/**
	 * 从默认总线上下线
	 * @param listener
	 */
	public void unregisterAsyncEventBus(Object listener) {
		defaultAsyncEventBus.unregister(listener);
	}
	
	/**
	 * 从指定的总线上下线
	 * @param eventBusName
	 * @param listener
	 */
	public void unregisterEventBus(String eventBusName, Object listener) {
		if(SMTHIT_DEFAULT_SYNC_BUS.equalsIgnoreCase(eventBusName)) {
			defaultSyncEventBus.unregister(listener);
			return;
		} 
		
		if(SMTHIT_DEFAULT_ASYNC_BUS.equalsIgnoreCase(eventBusName)) {
			defaultAsyncEventBus.unregister(listener);
			return;
		} 
		
		throw new ServiceException(MessageFormat.format("{0}消息中线不存在", eventBusName));
	}
	
	/**
	 * 获取默认总线
	 * @return
	 */
	public EventBus getDefaultEventBus() {
		return defaultSyncEventBus;
	}
	
	/**
	 * 获取默认的异步消息总线
	 * @return
	 */
	public EventBus getDefaultAsyncEventBus() {
		return defaultAsyncEventBus;
	}
	
	/**
	 * 获取指定的总线
	 * @param eventBusName
	 * @return
	 */
	public EventBus getEventBus(String eventBusName) {
		if(SMTHIT_DEFAULT_SYNC_BUS.equalsIgnoreCase(eventBusName)) {
			return defaultSyncEventBus;
		} 
		
		if(SMTHIT_DEFAULT_ASYNC_BUS.equalsIgnoreCase(eventBusName)) {
			return defaultAsyncEventBus;
		} 
		
		throw new ServiceException(MessageFormat.format("{0}消息中线不存在", eventBusName));
	}
}
