package cn.smthit.v4.eventbus;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/12/14  22:59
 */
@FunctionalInterface
public interface EventPoster {
    /**
     * 执行方法
     */
    void execute();
}
