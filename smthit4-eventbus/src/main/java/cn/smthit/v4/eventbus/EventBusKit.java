package cn.smthit.v4.eventbus;

import com.google.common.eventbus.EventBus;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/11/8  20:20
 */
public class EventBusKit {
    private static SmthitEventBusFactory eventBusFactory;
    private static ReentrantLock lock = new ReentrantLock();

    public static SmthitEventBusFactory getEventBusFactory() {

        if(eventBusFactory != null) {
            return eventBusFactory;
        }

        lock.lock();

        try {
            eventBusFactory = new SmthitEventBusFactory();
            eventBusFactory.init();
        } finally {
            lock.unlock();
        }
        return eventBusFactory;
    }

    public static EventBus getEventBus() {
        return eventBusFactory.getDefaultAsyncEventBus();
    }

    /**
     * 在事务提交之后执行异步方法
     * @param task
     */
    public static void postAfterCommit(EventPoster task) {
        if(task == null) {
            return;
        }

        if(TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    task.execute();
                }
            });
        } else {
            task.execute();
        }
    }

    public static void postAfterCommit(Object event) {
        if(event == null) {
            return;
        }

        postAfterCommit(() -> {
            getEventBus().post(event);
        });
    }
}