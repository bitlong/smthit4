package cn.smthit.v4.web.log;

import cn.smthit.v4.common.lang.log.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/17  10:40
 */
public interface UserWebContextParser {
    UserInfo getUserInfo(HttpServletRequest request);
}
