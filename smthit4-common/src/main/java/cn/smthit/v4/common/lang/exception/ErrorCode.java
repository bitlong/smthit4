package cn.smthit.v4.common.lang.exception;

import cn.smthit.v4.common.lang.enums.IEnumStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Bean
 */
@Getter
@AllArgsConstructor
public enum ErrorCode implements IEnumStatus<String> {
    DEFAULT_ERROR("500", "服务器内部错误"),
    RESUBMIT_ERROR("COM-RESUBMIT", "重复提交, 请求正在执行中"),
    OBJECT_NOT_FOUND("COM-404", "对象不存在"),
    CLASS_NOT_FOUND("COM-405", "类不存在"),
    ASSERT_FAILED("COM-400", "数据验证失败"),
    DATA_PARSE_ERROR("COM-1000", "数据解析失败"),
    DAL_ERROR("COM-DAL-ACCESS-FAILED", "数据库操作失败");
    private String value;
    private String desc;
}

