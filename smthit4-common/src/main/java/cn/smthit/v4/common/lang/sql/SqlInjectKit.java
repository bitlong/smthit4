package cn.smthit.v4.common.lang.sql;

import cn.smthit.v4.common.lang.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/26
 */
@Slf4j
public class SqlInjectKit {
    public static final String TABLE_DICT_SIGN_SALT = "20200626";

    private static final String xssStr = "'|and |exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |;|or |+|,";

    private static final String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

    /**
     * 表示忽略大小写
     */
    private static final Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

    /**
     * sql注入过滤处理，遇到注入关键字抛异常
     *
     * @param value
     * @return
     */
    public static void checkXssSqlParam(String value) {
        if (value == null || "".equals(value)) {
            return;
        }
        // 统一转为小写
        value = value.toLowerCase();
        String[] xssArr = xssStr.split("\\|");
        for (int i = 0; i < xssArr.length; i++) {
            if (value.indexOf(xssArr[i]) > -1) {
                log.info("请注意，存在SQL注入关键词---> {}", xssArr[i]);
                log.info("请注意，值可能存在SQL注入风险!---> {}", value);
                throw new ServiceException("请注意，值可能存在SQL注入风险!--->" + value);
            }
        }
    }

    /**
     * sql注入过滤处理，遇到注入关键字抛异常
     *
     * @param values
     * @return
     */
    public static void checkXssSqlParam(String[] values) {
        String[] xssArr = xssStr.split("\\|");
        for (String value : values) {
            if (value == null || "".equals(value)) {
                return;
            }
            // 统一转为小写
            value = value.toLowerCase();
            for (int i = 0; i < xssArr.length; i++) {
                if (value.indexOf(xssArr[i]) > -1) {
                    log.warn("请注意，存在SQL注入关键词---> {}", xssArr[i]);
                    log.warn("请注意，值可能存在SQL注入风险!---> {}", value);
                    throw new ServiceException("请注意，值可能存在SQL注入风险!--->" + value);
                }
            }
        }
    }

    /**
     * SQL参数校验
     * @param paramVal ep: "or 1=1"
     */
    public static boolean checkSqlParam(String paramVal) {
        Matcher matcher = sqlPattern.matcher(paramVal);
        if (matcher.find()) {
            //获取非法字符：or
            log.warn("参数存在非法字符，请确认：" + matcher.group());
            return false;
        }

        return true;
    }
}
