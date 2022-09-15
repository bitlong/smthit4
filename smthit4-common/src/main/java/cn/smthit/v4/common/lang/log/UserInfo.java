package cn.smthit.v4.common.lang.log;

import lombok.Data;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/17  10:37
 */
@Data
public class UserInfo {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;
    /**
     * 扩展信息
     */
    private String extra;
}