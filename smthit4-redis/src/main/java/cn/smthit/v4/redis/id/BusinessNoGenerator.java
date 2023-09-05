package cn.smthit.v4.redis.id;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/18  12:23
 */
@Slf4j
public class BusinessNoGenerator {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param businessNoPrefix 业务前缀
     * @param businessCode 业务类型枚举
     * @param digit        业务序号位数
     * @return
     */
    public String generate(String businessNoPrefix, int businessCode, Integer digit) {
        if(digit == null) {
            digit = 6;
        }

        String date = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        String key = businessNoPrefix + businessCode + ":" + date;
        Long increment = redisTemplate.opsForValue().increment(key);

        return String.format("%d-%s-%s", businessCode, date, String.format("%0" + digit + "d", increment));
    }

    public String generate(String businessNoPrefix, int businessCode) {
        Integer defaultDigit = 6;
        return generate(businessNoPrefix, businessCode, defaultDigit);
    }
}