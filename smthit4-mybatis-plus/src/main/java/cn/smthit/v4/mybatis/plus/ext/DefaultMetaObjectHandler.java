package cn.smthit.v4.mybatis.plus.ext;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;
import java.util.Objects;

/**
 * @description: 设置公用字段的默认值
 * @author: Bean
 * @date: 2022/9/19  11:14
 */
public class DefaultMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createdAt = this.getFieldValByName("createdAt", metaObject);
        if (Objects.isNull(createdAt)) {
            // 填充
            this.setFieldValByName("createdAt", new Date(), metaObject);
        }

        Object updatedAt = this.getFieldValByName("updatedAt", metaObject);
        if (Objects.isNull(updatedAt)) {
            // 填充
            this.setFieldValByName("updatedAt", new Date(), metaObject);
        }

        Object status = this.getFieldValByName("status", metaObject);
        if(Objects.isNull(status)) {
            this.setFieldValByName("status", new Integer(1), metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatedAt",new Date(), metaObject);
    }
}