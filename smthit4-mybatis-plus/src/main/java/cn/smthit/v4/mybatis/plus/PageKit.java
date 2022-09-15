package cn.smthit.v4.mybatis.plus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/15  18:26
 */
public class PageKit {
    /**
     * 创建一个Page对象
     * @param current
     * @param size
     * @param <T>
     * @return
     */
    public static <T> Page<T> newPage(int current, int size) {
        return new Page<T>(current, size);
    }
}