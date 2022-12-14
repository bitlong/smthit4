package cn.smthit.v4.web.kits;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/12/14  23:14
 */
@FunctionalInterface
public interface TaskAfterCommit {
    void execute();
}
