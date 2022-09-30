package cn.smthit.v4.mybatis.plus;


import cn.smthit.v4.common.lang.convert.AbstractConvert;
import cn.smthit.v4.common.lang.convert.DefaultConvert;
import cn.smthit.v4.common.lang.data.PageData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/15  18:26
 */
public class PageKit {

    public static <T> Page<T> newPage(int current, int size) {
        return new Page<T>(current, size);
    }
}