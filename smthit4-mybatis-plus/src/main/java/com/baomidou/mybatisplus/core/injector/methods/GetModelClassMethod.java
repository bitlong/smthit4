package com.baomidou.mybatisplus.core.injector.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/9/19  0:25
 */
public class GetModelClassMethod extends AbstractMethod {
    protected GetModelClassMethod() {
        super("getModelClassMethod");
    }

    protected GetModelClassMethod(String methodName) {
        super(methodName);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        return null;
    }
}