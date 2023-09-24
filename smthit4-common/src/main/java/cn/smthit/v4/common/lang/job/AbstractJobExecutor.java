package cn.smthit.v4.common.lang.job;

import cn.smthit.v4.common.lang.data.Result;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/11/4  11:56
 */
@Slf4j
public abstract class AbstractJobExecutor implements JobExecutor {
    @Override
    public void execute() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Result<?> result = Result.failed();
        try  {
            log.debug("开始执行任务 {}", getName());
            result = doExecute();
        } catch (Throwable exp) {
            log.error(exp.getMessage(), exp);
        } finally {
            long duration = stopwatch.stop().elapsed(TimeUnit.SECONDS);
            log.debug("任务 {} 完成，耗时时长 {} 秒, Result: {}", getName(), duration, result.toString());
        }
    }

    public abstract Result<?> doExecute();
}