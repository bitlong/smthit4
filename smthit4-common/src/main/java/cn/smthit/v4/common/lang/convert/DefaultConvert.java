/**
 * 
 */
package cn.smthit.v4.common.lang.convert;

import cn.smthit.v4.common.lang.exception.ServiceException;
import cn.smthit.v4.common.lang.kits.BeanKit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bean
 *
 */
@Slf4j
public class DefaultConvert<PO, VO> extends AbstractConvert<PO, VO> {
	protected Class<VO> voCls;

	public DefaultConvert(Class<VO> cls) {
		this.voCls = cls;
	}

	@Override
	public VO toVO(PO po) {
		if(po == null) {
			return null;
		}
		
		try {
			VO vo = (VO)voCls.newInstance();
			BeanKit.copyPropertiesFromBean2Bean(po, vo);
			
			return vo;
		} catch (Exception exp) {
			log.error("对象转换失败.", exp);
			throw new ServiceException("从PO转换VO失败, VO class path = " + voCls.getCanonicalName() + " :" + exp.getMessage());
		}
	}
}
