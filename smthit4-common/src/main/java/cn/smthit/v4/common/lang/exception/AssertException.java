/**
 * 
 */
package cn.smthit.v4.common.lang.exception;

/**
 * @author Bean
 *
 */
public class AssertException extends ServiceException {
	private static final long serialVersionUID = -162456259376571319L;

	/**
	 * @param detailMessage
	 */
	public AssertException(String detailMessage) {
		super(detailMessage);
		setCode(ErrorCode.ASSERT_FAILED.getValue());
		setMessage(ErrorCode.ASSERT_FAILED.getDesc());
	}
}
