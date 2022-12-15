package cn.smthit.v4.web.kits;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/12/15  11:08
 */
public class WebKit {

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return request.getHeader("X-Requested-With") != null &&
                "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public static boolean isJsonRequest(HttpServletRequest request) {
        return request.getHeader("Content-Type") != null &&
                request.getHeader("Content-Type").startsWith("application/json");
    }

}