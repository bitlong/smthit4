/**
 * 
 */
package cn.smthit.v4.common.lang.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author Bean
 * @deprecated 使用Result<T>类来代替
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
@NoArgsConstructor
@lombok.experimental.Accessors(chain = true)
public class Response<T> {
	public static final int SUCCESS = 200;
	public static final int DEFAULT_ERROR = 500;
	
	public static <T> Response<T> newSuccess() {
		Response<T> rd = new Response<>(true, null);
		
		rd.setStatus(SUCCESS);
		rd.setMessage("Success");
		
		return rd;
	}
	
	public static <T> Response<T> newSuccess(String message) {
		Response<T> rd = new Response<>(true, null);
		rd.setStatus(SUCCESS);
		rd.setMessage(message);
		
		return rd;
	}
	
	
	public static <T> Response<T> newSuccess(T data) {
		return Response.newSuccess("", data);
	}
	
	public static <T> Response<T> newSuccess(String message, T data) {
		Response<T> rd = new Response<>(true, data);
		rd.setStatus(SUCCESS);
		rd.setMessage(message);
		
		return rd;
	}
	
	public static <T> Response<T> newFailed() {
		Response<T> rd = new Response<>(false, null);
		rd.message = "Failed";
		rd.data = null;
		rd.setStatus(DEFAULT_ERROR);
		return rd;
	}
	
	public static <T> Response<T> newFailed(String message) {
		Response<T> rd = new Response<>(false, null);
		rd.message = message;
		rd.data = null;
		rd.setStatus(DEFAULT_ERROR);
		return rd;
	}
	
	public static <T> Response<T> newFailed(T data) {
		return Response.newFailed("", data);
	}
	
	public static <T> Response<T> newFailed(String message, T data) {
		Response<T> rd = new Response<>(false, data);
		rd.message = message;
		rd.data = data;
		rd.setStatus(DEFAULT_ERROR);
		return rd;
	}
	
	private boolean success;
	private T data;
	private String message;
	private int status;
	
	private List<String> errors;
	private Map<String, String> fieldErrors;
	
	public Response(boolean success, T data) {
		this.success = success;
		this.data = data;
	}
}
