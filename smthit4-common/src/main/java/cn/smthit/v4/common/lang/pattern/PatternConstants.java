package cn.smthit.v4.common.lang.pattern;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/26
 */
public class PatternConstants {

    /**
     * 变量
     */
    public static final String PATTERN_VAR = "^[a-zA-Z0-9]+$";

    //邮件
    public static final String PATTERN_EMAIL = "[a-zA-Z_]{0,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";

    //SQL注入 TODO
    public static final String PATTERN_SQL_FILTER = "(?:')|(?:--)|(/\\\\*(?:.|[\\\\n\\\\r])*?\\\\*/)|\"\n" +
            "\t\t\t+ " +
            "\"(\\\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\\\b)";

    //手机号
    public static final String PATTERN_MOBILE = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    //数字-整数

    //字母

    //账户是否合法-帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)
    public static final String PATTERN_USERNAME = "18[a-zA-Z0-9_]{4,15}$";

    //URL
    public static final String PATTERN_URL = "[a-zA-z]+://[^\\s]*";

    //域名
    public static final String PATTERN_DOMAIN = "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+.?";
}
