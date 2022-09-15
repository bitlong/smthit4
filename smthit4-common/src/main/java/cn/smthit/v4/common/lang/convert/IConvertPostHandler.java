/**
 * 
 */
package cn.smthit.v4.common.lang.convert;

/**
 * @author Bean
 *
 */
public interface IConvertPostHandler<PO, VO> {
	public void post(PO po, VO vo);
}
