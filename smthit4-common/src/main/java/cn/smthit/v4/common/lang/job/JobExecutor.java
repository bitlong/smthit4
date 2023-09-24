package cn.smthit.v4.common.lang.job;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/11/4  11:54
 */
public interface JobExecutor {
    /**
     * 任务名称
     * @return
     */
    String getJobName();

    String getJobKey();

    /**
     * 任务执行接口
     */
    void execute();
}