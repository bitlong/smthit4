package cn.smthit.v4.web.log;

import cn.smthit.v4.common.lang.log.UserInfo;
import jakarta.servlet.http.HttpServletRequest;


/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/17  10:40
 */
public interface UserWebContextParser {
    UserInfo getUserInfo(HttpServletRequest request);
}
