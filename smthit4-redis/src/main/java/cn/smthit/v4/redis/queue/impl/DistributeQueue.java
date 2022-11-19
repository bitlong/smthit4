package cn.smthit.v4.redis.queue.impl;

import cn.smthit.v4.redis.queue.IDistributedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/11/15  11:39
 */
public class DistributeQueue implements IDistributedQueue {
    @Autowired
    private RedissonClient redissonClient;
}