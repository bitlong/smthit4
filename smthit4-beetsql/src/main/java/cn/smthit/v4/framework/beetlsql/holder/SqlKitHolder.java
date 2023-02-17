/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.holder;

import java.util.ArrayList;
import java.util.List;

import cn.smthit.v4.common.lang.exception.ServiceException;
import cn.smthit.v4.framework.beetlsql.bettlsql.SqlKit;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.starter.BeetlSqlConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;



import lombok.Data;

/**
 * @author Bean
 * TODO 数据源的方式有多种，需要考虑怎么扩展
 * 对等模式，Master-Slave模式， 主读写-从只读，分库分表（shard模式）
 */
@Data
public class SqlKitHolder implements ApplicationContextAware, EnvironmentAware {
	
	private List<String> multiDS = new ArrayList<String>();
	
	/**
	 * 主SQLManager
	 */
	private String masterSqlManager = "sqlManager";
	
	private Environment env;
	
	private BeetlSqlConfig beetlSqlConfig ;

	public SqlKitHolder() {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		beetlSqlConfig = new BeetlSqlConfig(env);
		
		beetlSqlConfig.getConfigs().entrySet().forEach(entry-> {
			String sqlManagerName = entry.getKey();
			SQLManager sqlManager = (SQLManager) applicationContext.getBean(sqlManagerName, SQLManager.class);
			SqlKit.$(sqlManagerName, sqlManager);
			if(masterSqlManager.equals(sqlManagerName)) {
				SqlKit.$(sqlManager);
			}
		});
		
		if(SqlKit.$() == null) {
			throw new ServiceException("Not Set Default SQLManager");
		}
	}
	
	@Override
	public void setEnvironment(Environment env) {
		this.env = env;

	}
}
