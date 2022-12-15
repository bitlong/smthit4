package cn.smthit.v4.web.kits;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @description: 执行任务小工具
 * @author: Bean
 * @date: 2022/12/14  23:14
 */
@Slf4j
public class ExecuteKit {
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

    public static void postAfterCommit(TaskAfterCommit task, boolean async) {
        return;
    }
}