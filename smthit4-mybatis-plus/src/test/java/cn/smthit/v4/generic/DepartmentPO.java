package cn.smthit.v4.generic;

import cn.smthit.v4.mybatis.plus.EntityMapper;
import org.w3c.dom.Entity;

import static cn.smthit.v4.mybatis.plus.SqlKit.mapper;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/9/18  22:31
 */
public class DepartmentPO {
    private Long id;
    private String name;

    interface Dao extends EntityMapper<DepartmentPO> {
        Dao $ = (Dao)mapper(Dao.class);
    }
}