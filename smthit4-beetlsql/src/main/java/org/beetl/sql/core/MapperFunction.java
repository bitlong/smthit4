/**
 * 
 */
package org.beetl.sql.core;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.sql.core.engine.SQLPlaceholderST;

/**
 * @author Bean
 * TODO 未实现
 */
public class MapperFunction implements Function {
	
	static {
        SQLPlaceholderST.textFunList.add("mapper");
	}	
	
	@Override
	public Object call(Object[] paras, Context ctx) {
		throw new UnsupportedOperationException();
	}
}