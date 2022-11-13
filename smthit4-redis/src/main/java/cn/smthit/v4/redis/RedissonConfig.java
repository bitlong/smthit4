package cn.smthit.v4.redis;

import cn.hutool.core.util.StrUtil;
import cn.smthit.v4.redis.lock.impl.RedissonDistributedLocker;
import lombok.Setter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import javax.annotation.Resource;

/**
 * 分布式锁 Redisson 配置
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2021/2/22
 */
@ConditionalOnProperty(prefix = "redisson",name = "address")
@ConfigurationProperties(prefix = "redisson")
@Configuration
public class RedissonConfig {
    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedissonDistributedLocker redissonDistributedLocker() {
        return new RedissonDistributedLocker();
    }

}