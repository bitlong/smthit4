package cn.smthit.v4.feign;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;

/**
 * @description: ...
 * smthit.feign.server.enabled=true
 * smthit.feign.client.enabled=true
 * @author: Bean
 * @date: 2022/9/16  12:58
 */
@Import(SmthitFeignServerConfiguration.class)
@ConditionalOnProperty(prefix = "smthit.feign.server",name = "enabled",havingValue = "true", matchIfMissing = true)
public class SmthitFeignServerAutoConfiguration {
}