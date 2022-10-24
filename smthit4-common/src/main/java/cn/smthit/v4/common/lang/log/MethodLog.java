package cn.smthit.v4.common.lang.log;

import lombok.Data;
import lombok.ToString;
import lombok.extern.java.Log;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/17  9:58
 */
@lombok.experimental.Accessors(chain = true)
@Data
@ToString
public class MethodLog {
    private String module;
    private String operate;
    private String interfaceName;
    private Object returnData;
    private Long costTime;
    private UserInfo userInfo;
}