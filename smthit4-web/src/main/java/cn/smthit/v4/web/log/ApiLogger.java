package cn.smthit.v4.web.log;

import java.lang.annotation.*;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/17  9:22
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLogger {
}
