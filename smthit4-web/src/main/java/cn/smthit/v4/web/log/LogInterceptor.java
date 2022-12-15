/**
 * 
 */
package cn.smthit.v4.web.log;

import cn.smthit.v4.common.lang.kits.GsonKit;
import cn.xueda.aiyou.mbus.SystemEvent;
import cn.xueda.aiyou.mbus.SystemEventKit;
import cn.xueda.aiyou.spi.enums.system.EnumEventLevel;
import cn.xueda.aiyou.spi.enums.system.EnumEventLogType;
import cn.xueda.aiyou.spi.enums.system.EnumEventSource;
import com.smthit.lang.json.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bean
 *
 */
@Slf4j
public class LogInterceptor extends HandlerInterceptorAdapter {
	private static Logger apiLogger = LoggerFactory.getLogger("web.api.logger");

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		super.afterCompletion(request, response, handler, ex);

		if((request.getMethod().equals("GET") ||
				request.getMethod().equals("POST") ||
				request.getMethod().equals("DELETE") ||
				request.getMethod().equals("PUT"))) {

			StringBuffer sb = new StringBuffer();
			Map<String, Object> content = new HashMap<>(10);

			content.put("url", request.getRequestURI() + request.getContextPath());
			content.put("query_string", request.getQueryString());
			content.put("content_type", request.getContentType());
			content.put("request_content", getRequestBody(request));
			content.put("response_content", getResponseBody(response));
			log.debug(GsonKit.toJson(content));
		}
	}
	
    private String getRequestBody(HttpServletRequest request) {
        String requestBody = "";

        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            try {
                requestBody = IOUtils.toString(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
            } catch (IOException e) {
                // NOOP
            }
        }
        
        return requestBody;
    }

	private String getResponseBody(HttpServletResponse response) {
		String responseBody = "";
		ContentCachingRequestWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
		if (wrapper != null) {
			try {
				responseBody = IOUtils.toString(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
			} catch (IOException e) {
				// NOOP
			}
		}

		return responseBody;
	}
}
