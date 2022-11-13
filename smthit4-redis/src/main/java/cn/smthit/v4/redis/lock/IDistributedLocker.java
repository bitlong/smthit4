package cn.smthit.v4.redis.lock;

import java.util.concurrent.TimeUnit;

/**
 * @description: 分布式锁操作接口
 * @author: Bean
 * @date: 2022/11/13  11:49
 */
public interface IDistributedLocker {
    /**
     * 加锁
     * @param key
     */
    void lock(String key);

    /**
     * 解锁
     * @param key
     */
    void unlock(String key);

    /**
     * 加锁，等待timeout,默认秒
     * @param key
     * @param timeout
     */
    void lock(String key, int timeout);

    /**
     * 加锁，等待timeout，单位根据timeUnit进行设置
     * @param key
     * @param timeUnit
     * @param timeout
     */
    void lock(String key, TimeUnit timeUnit, int timeout);


    /**
     * 尝试获取锁,获得返回true，否则返回false
     * @param key
     */
    boolean tryLock(String key);

    /**
     * 尝试获取锁，获得锁后持有 leaseTime（指定时长）
     * @param key
     * @param waitTime
     * @param leaseTime
     * @param timeUnit
     * @return
     */
    boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException;

    /**
     * 锁是否已经被其他的线程持有
     * @param key
     * @return
     */
    boolean isLocked(String key);
}
