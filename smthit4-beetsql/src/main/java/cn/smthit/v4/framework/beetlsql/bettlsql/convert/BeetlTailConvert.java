/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.bettlsql.convert;

import java.util.Map;

import cn.smthit.v4.common.lang.convert.DefaultConvert;
import cn.smthit.v4.common.lang.kits.BeanKit;
import org.beetl.sql.core.TailBean;




/**
 * @author Bean
 *
 */
public class BeetlTailConvert<PO, VO> extends DefaultConvert<PO, VO> {

	public BeetlTailConvert(Class<VO> cls) {
		super(cls);
	}

	@Override
	public VO toVO(PO po) {
		if(po == null) {
			return null;
		}
		
		VO vo = super.toVO(po);

		if (TailBean.class.isAssignableFrom(po.getClass())) {
			TailBean tail = (TailBean) po;

			Map<String, Object> tails = tail.getTails();
			
			BeanKit.copyPropertiesFromMap2Bean(vo, tails);
		}

		return vo;
	}

}
