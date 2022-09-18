package cn.smthit.v4.web.web.exception;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/16  15:49
 */
import cn.smthit.v4.common.lang.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 控制器异常处理
 *
 * @author haoyun.zheng
 */
public class DefaultErrorController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    /**
     * 页面错误处理
     *
     * @author haoyun.zheng
     */
    @RequestMapping(value = "/error", produces = MediaType.TEXT_HTML_VALUE)
    public String errorHtml(HttpServletRequest request, Model model) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> body = this.errorAttributes.getErrorAttributes(webRequest, true);

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        model.addAttribute("body", body);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "errors/404";
            } else {
                model.addAttribute("exception", "页面请求失败，状态码:" + statusCode);
            }
        }

        return "/errors/500";
    }

    /**
     * 接口错误处理
     *
     * @author haoyun.zheng
     */
    @ResponseBody
    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<?> errorJson(HttpServletRequest request) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> body = this.errorAttributes.getErrorAttributes(webRequest, true);

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return Result.failed()
                        .detailMessage("404 错误, 请求地址不存在. 地址: " + request.getRequestURL())
                        .code("404")
                        .message("请求地址不存在")
                        .data(body);
            }

            return Result.failed()
                    .code("HTTP-" + statusCode)
                    .message(HttpStatus.resolve(statusCode).getReasonPhrase())
                    .detailMessage("HTTP 错误, 错误码：" + statusCode + ", 原因: " + HttpStatus.resolve(statusCode).getReasonPhrase())
                    .data(body);
        }

        return Result.failed()
                .code("UNKOWN-ERROR")
                .message("未知HTTP错误")
                .data(body);
    }

    /**
     * 返回错误的路径
     *
     * @author haoyun.zheng
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
}