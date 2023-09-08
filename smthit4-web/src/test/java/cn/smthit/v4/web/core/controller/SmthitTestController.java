package cn.smthit.v4.web.core.controller;

import cn.smthit.v4.common.lang.data.Result;
import cn.smthit.v4.common.lang.kits.DateKit;
import cn.smthit.v4.web.core.UserPageQueryParam;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.Optional;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/8
 */
public class SmthitTestController {

    @PostMapping("/pageUser")
    public Result<?> pageUser(UserPageQueryParam param) {

        Date maxDate = Optional.of(param.getCreateDate().getMax()).orElse(new Date());
        Date minDate = Optional.of(param.getCreateDate().getMax()).orElse(DateKit.parseDate("2022-02-11 00:00:00"));
        return Result.ok();
    }

}
