package cn.smthit.v4.web.kits;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.*;

/**
 * @description: 执行任务小工具
 * @author: Bean
 * @date: 2022/12/14  23:14
 */
@Slf4j
public class ExecuteKit {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 20,
            60, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(1000),
            new ThreadPoolExecutor.CallerRunsPolicy());

    //private static ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(20);

    /**
     * 如果在事务中，则事务提交之后执行task
     * 如果不在事务中，则直接执行
     * @param task
     */
    public static void postAfterCommit(TaskAfterCommit task) {

        if(task == null) {
            return;
        }

        if(TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    try {
                        task.execute();
                    } catch (Exception exp) {
                        log.error(exp.getMessage(), exp);
                    }
                }
            });
        } else {
            task.execute();
        }
    }

    /**
     * 事务提交后异步执行
     * @param task
     * @param async
     */
    public static void postAfterCommit(TaskAfterCommit task, boolean async) {
        if(task == null) {
            return;
        }

        if(async == true) {
            if(TransactionSynchronizationManager.isActualTransactionActive()) {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        threadPoolExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    task.execute();
                                } catch (Exception exp) {
                                    log.error(exp.getMessage(), exp);
                                }
                            }
                        });
                    }
                });
            } else {
                task.execute();
            }
        } else {
            postAfterCommit(task);
        }
    }
}