package cn.smthit.v4.common.lang.log;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/17  10:01
 */
public interface MethodLogListener {
    void onLog(MethodLog apiLog);
    void onException(Throwable throwable);
}
