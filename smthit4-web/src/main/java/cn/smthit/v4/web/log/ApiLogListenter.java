package cn.smthit.v4.web.log;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/17  9:33
 */
public interface ApiLogListenter {
    void onLog(ApiLog apiLog);
    void onException(Throwable throwable);
}