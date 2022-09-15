package cn.smthit.v4.common.lang.exception;

import cn.smthit.v4.common.lang.enums.IEnumStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.xml.bind.annotation.XmlType;

@Getter
@AllArgsConstructor
public enum ErrorCode implements IEnumStatus<String> {
    DEFAULT_ERROR("500", "服务器内部错误"),
    OBJECT_NOT_FOUND("COM-404", "对象不存在"),
    ASSERT_FAILED("COM-400", "数据验证失败"),
    DATA_PARSE_ERROR("COM-1000", "数据解析失败");;

    private String value;
    private String desc;
}

