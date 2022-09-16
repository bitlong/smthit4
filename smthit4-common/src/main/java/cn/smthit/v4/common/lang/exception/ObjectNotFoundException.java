/**
 * 
 */
package cn.smthit.v4.common.lang.exception;

/**
 * @author Bean
 *
 */
public class ObjectNotFoundException extends ServiceException {
	private static final long serialVersionUID = -4502215380498818784L;
	
	public ObjectNotFoundException(String detailMessage) {
		super("对象不存在.");
		setCode(ErrorCode.OBJECT_NOT_FOUND.getValue());
		setMessage(ErrorCode.OBJECT_NOT_FOUND.getDesc());
		setDetailMessage(detailMessage);
	}
}
