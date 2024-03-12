package cn.smthit.v4.web.mybatis;

import cn.smthit.v4.mybatis.plus.SqlKit;
import lombok.Data;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/12  19:14
 */
@Data
public class SqlKitHolder implements ApplicationContextAware, EnvironmentAware {
    private static Logger logger = LoggerFactory.getLogger(SqlKitHolder.class);
    private static ApplicationContext applicationContext;
    private static Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SqlKitHolder.applicationContext != null) {
            return;
        }

        SqlKitHolder.applicationContext = applicationContext;

    }

    @Override
    public void setEnvironment(Environment environment) {
        if (SqlKitHolder.environment != null) {
            return;
        }
        SqlKitHolder.environment = environment;
    }

    public void init(Configuration properties) {
        SqlSessionTemplate sqlSessionTemplate = applicationContext.getBean(SqlSessionTemplate.class);
        if (properties != null) {
            SqlKit.initSqlKit(properties, sqlSessionTemplate);
        } else {
            logger.error("初始化SqlKit失败, 没有找到SqlSessionTemplate");
        }
    }
}