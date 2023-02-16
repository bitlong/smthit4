/**
 * 
 */
package com.smthit.framework.dal.bettlsql.convert;

import cn.smthit.v4.common.lang.convert.DefaultConvert;
import cn.smthit.v4.common.lang.convert.ExtBean;
import org.beetl.sql.core.TailBean;

/**
 * @author Bean
 *
 */
public class BeetlConverter<PO, VO> extends DefaultConvert<PO, VO> {

	public BeetlConverter(Class<VO> cls) {
		super(cls);
	}

	@Override
	public VO toVO(PO po) {
		if(po == null) {
			return null;
		}
		
		VO vo = super.toVO(po);
		
		if(TailBean.class.isAssignableFrom(po.getClass()) &&
				ExtBean.class.isAssignableFrom(voCls)) {
			TailBean tail = (TailBean)po;
			ExtBean extBean = (ExtBean)vo;
			
			if(tail.getTails().size() > 0) {
				extBean.addAll(tail.getTails());
			}
		}
		
		return vo;
	}	
}
