package cn.smthit.v4.feign;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/12/3  16:18
 */
@Import(value = {SmthitFeignClientConfiguration.class})
@ConditionalOnProperty(prefix = "smthit.feign.client",name = "enabled", havingValue = "true", matchIfMissing = true)
public class SmthitFeignClientAutoConfiguration {
}