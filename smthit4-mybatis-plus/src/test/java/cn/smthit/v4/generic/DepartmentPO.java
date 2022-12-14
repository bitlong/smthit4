package cn.smthit.v4.generic;

import cn.smthit.v4.mybatis.plus.ext.EntityMapper;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.w3c.dom.Entity;

import static cn.smthit.v4.mybatis.plus.SqlKit.mapper;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/9/18  22:31
 */
public class DepartmentPO {
    @TableId
    private Long id;
    @TableField
    private String name;

    interface Dao extends EntityMapper<DepartmentPO> {
        Dao $ = (Dao)mapper(Dao.class);

        default Class<?> getMapperClass() {
            return Dao.class;
        }

    }
}