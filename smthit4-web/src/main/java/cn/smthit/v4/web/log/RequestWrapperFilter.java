/**
 * 
 */
package cn.smthit.v4.web.log;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bean
 *
 */
public class RequestWrapperFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		if(request.getContentType() != null &&
				request.getContentType().contains("application/json")) {
			HttpServletRequest request1 = (HttpServletRequest)request;
			HttpServletResponse response1 = (HttpServletResponse)response;
			
			ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request1);
	        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response1);
	        
	        try {
				chain.doFilter(requestWrapper, responseWrapper);
	        } finally {
	        	responseWrapper.copyBodyToResponse();
	        }
		} else {
			chain.doFilter(request, response);
		}
	}
}
