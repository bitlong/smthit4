/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.druid;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bean
 * 对链接池中信息进行脱敏
 */
@Slf4j
public class SecurityDataSource extends DruidDataSource {
	private static final long serialVersionUID = 4174843449761158318L;

	@Override
	public void setUsername(String username) {
		try {
			username = ConfigTools.decrypt(username);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		super.setUsername(username);
	}

	@Override
	public void setPassword(String password) {
		try {
			password = ConfigTools.decrypt(password);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		
		super.setPassword(password);
	}
	
    public static void main(String[] args) throws Exception{
        String password = "123456";
        String username = "root";
        System.out.println("加密后的password = [" + ConfigTools.encrypt(password) + "]");
        System.out.println("加密后的username = [" + ConfigTools.encrypt(username) + "]");
    }

}
