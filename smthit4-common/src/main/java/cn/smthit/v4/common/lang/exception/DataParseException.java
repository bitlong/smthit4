/**
 * 
 */
package cn.smthit.v4.common.lang.exception;

/**
 * @author Bean
 *
 */
public class DataParseException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6466423297340285300L;

	/**
	 * @param msg
	 */
	public DataParseException(String msg) {
		super(msg);
		setCode(ErrorCode.DATA_PARSE_ERROR.getValue());
		setMessage(ErrorCode.DATA_PARSE_ERROR.getDesc());
		setDetailMessage(msg);
	}
}
