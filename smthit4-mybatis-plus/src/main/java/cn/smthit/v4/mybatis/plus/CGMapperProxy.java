package cn.smthit.v4.mybatis.plus;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.Method;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/16  1:23
 */
public class CGMapperProxy<T> implements MethodInterceptor {

    private Object target;
    private Class<T> cls;

    CGMapperProxy(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if(target == null) {
            target = SqlKit.getMapper(cls);
        }
        if(target == null) {
            throw new RuntimeException("找不到对应的Mapper, Mapper: " + cls.getName());
        }

        //获取当前的模型类
        if(method.getName().equals("currentModelClass")) {
            Class<?> cls = GenericTypeResolver.resolveTypeArgument(target.getClass(), EntityMapper.class);
            return cls;
        }

        return method.invoke(target, args);

    }

    static Object getProxyMapper(Class<?> cls) {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(cls);
        enhancer.setCallback(new CGMapperProxy(cls));

        return enhancer.create();
    }
}