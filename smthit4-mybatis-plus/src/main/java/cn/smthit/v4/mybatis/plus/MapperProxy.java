package cn.smthit.v4.mybatis.plus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import cn.smthit.v4.mybatis.plus.ext.EntityMapper;
/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/15  23:58
 */
public class MapperProxy<T> implements InvocationHandler {
    private EntityMapper<?> target;
    private Class<T> cls;

    MapperProxy(Class<T> cls) {
        this.cls = cls;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(target == null) {
            target = SqlKit.getMapper(cls);
        }
        if(target == null) {
            throw new RuntimeException("找不到对应的Mapper, Mapper: " + cls.getName());
        }


        return method.invoke(target, args);
    }

    static  EntityMapper<?> getProxyMapper(Class<?> cls) {
        Object obj = Proxy.newProxyInstance(cls.getClassLoader(),
                cls.getInterfaces(), new MapperProxy(cls));
        //System.out.println(obj);
        return (EntityMapper<?> )obj;
    }
}