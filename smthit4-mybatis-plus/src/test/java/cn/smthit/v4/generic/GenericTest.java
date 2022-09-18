package cn.smthit.v4.generic;

import cn.smthit.v4.mybatis.plus.EntityMapper;
import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Stream;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/9/18  22:30
 */
public class GenericTest {

    public static void main(String args[]) {

        Class<?> superCls = DepartmentPO.Dao.class.getSuperclass();
        if(superCls != null) {
            System.out.println("super class name: " + superCls.getName());
        } else {
            System.out.println("super class is null");
        }

        //获取所有的泛型接口
        Type[] types = DepartmentPO.Dao.class.getGenericInterfaces();
        if(types != null) {
            Stream.of(types).forEach(type -> {
                System.out.println(type.getTypeName());
                ParameterizedType parameterizedType = (ParameterizedType)type;

                Class<?> cls = GenericTypeResolver.resolveTypeArgument(DepartmentPO.Dao.class, EntityMapper.class);
                System.out.println(cls.getName());

                Type[] actualTypes = parameterizedType.getActualTypeArguments();
                Stream.of(actualTypes).forEach(actualType-> {
                    System.out.println(actualType.getTypeName());


                });
            });
        }

        return;
    }
}