package cn.smthit.v4.mybatis.plus;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/12  19:01
 */
public class SqlKit {

    private static Configuration mybatisConfigration;
    private static SqlSessionTemplate sqlSessionTemplate;

    public static void initSqlKit(Configuration mybatisConfiguration, SqlSessionTemplate sqlSessionTemplate) {
        SqlKit.mybatisConfigration = mybatisConfiguration;
        SqlKit.sqlSessionTemplate = sqlSessionTemplate;
    }

    public static <T> EntityMapper<T> getMapper(Class<T> cls) {
        if(mybatisConfigration == null || sqlSessionTemplate == null) {
            throw new RuntimeException("SqlKit还没有被正确的初始化...");
        }

        return (EntityMapper<T>) mybatisConfigration.getMapper(cls, sqlSessionTemplate);
    }

    public static Object mapper(Class<?> cls) {
        //return MapperProxy.getProxyMapper(cls);
        return CGMapperProxy.getProxyMapper(cls);
    }

    public static SqlSessionTemplate $() {
        return sqlSessionTemplate;
    }
}