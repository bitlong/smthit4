package cn.smthit.v4.redis.lock.impl;

import cn.smthit.v4.redis.lock.IDistributedLocker;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/11/13  11:51
 */
public class RedissonDistributedLocker implements IDistributedLocker {
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void lock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.lock();
    }

    @Override
    public void unlock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }

    @Override
    public void lock(String key, int timeout) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public void lock(String key, TimeUnit timeUnit, int timeout) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(timeout, timeUnit);
    }

    @Override
    public boolean tryLock(String key) {
        RLock lock = redissonClient.getLock(key);
        return lock.tryLock();
    }

    @Override
    public boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
        RLock lock = redissonClient.getLock(key);
        return lock.tryLock(waitTime, leaseTime, timeUnit);
    }

    @Override
    public boolean isLocked(String key) {
        RLock lock = redissonClient.getLock(key);
        return lock.isLocked();
    }
}