package cn.smthit.v4.mybatis.plus.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.*;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * SQL注入攻击过滤
 *
 * @author bean
 * @date 2023/10/7
 */
@Slf4j
public class SqlInjectInterceptor implements Interceptor {

    private static Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

    public static String[] KEYWORDS =
            ";|'|\"|\\|--|_|*|%|#|//|+|=|or|and|like|select|insert|update|delete|alert|drop|truncate|declare|exec|execute|create|xp_|sp_|0x"
            .split("\\|");

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object parameterObject = invocation.getArgs()[1];

        if (null != parameterObject) {
            MetaObject mappedStatement = SystemMetaObject.forObject(invocation.getArgs()[0]);

            //动态SQL
            if (mappedStatement.getValue("sqlSource") instanceof DynamicSqlSource) {
                Set<String> parameterKeys = (Set<String>) cache.get((String) mappedStatement.getValue("id"));
                if (null == parameterKeys) {
                    SqlNode rootSqlNode = (SqlNode) mappedStatement.getValue("sqlSource.rootSqlNode");
                    parameterKeys = parseSqlNode(rootSqlNode);
                    cache.put((String) mappedStatement.getValue("id"), parameterKeys);
                }

                if (null != parameterKeys && !parameterKeys.isEmpty()) {
                    MetaObject parameterMo = SystemMetaObject.forObject(parameterObject);
                    for (String parameterKey : parameterKeys) {
                        if (parameterMo.hasGetter(parameterKey) || parameterObject instanceof Map) {
                            Object value = parameterMo.getValue(parameterKey);
                            if (null != value && !"".equals(value)) {
                                parameterMo.setValue(parameterKey, process(value));
                            }
                        }
                    }
                }
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object arg0) {
        if (arg0 instanceof Executor) {
            return Plugin.wrap(arg0, this);
        }
        return arg0;
    }

    public void setKeywords(String keywords) {
        if (StringUtils.isNotEmpty(keywords)) {
            SqlInjectInterceptor.KEYWORDS = keywords.split("\\|");
        }
    }

    private static Object process(Object value) {
        for (int i = 0; i < KEYWORDS.length; i++) {
            String str = KEYWORDS[i];
            //i==11 or 之后的串，包含or。关键词
            if (i > 11) {
                //TODO 这里需要优化
                if (Pattern.compile("\\s+").matcher(value.toString()).find()) {
                    for (String s : value.toString().split("\\s+")) {
                        if (StringUtils.equalsIgnoreCase(s, str)) {
                            value = value.toString().replaceAll(str, "N/A");
                            log.warn("sql脚本中特殊字符【{}】已被过滤", str);
                        }
                    }
                }
            } else if (value.toString().toLowerCase().contains(str)) {
                //i <=11 or之前的串，特殊的符号，直接过滤掉
                value = value.toString().replaceAll(Pattern.quote(str), "N/A");
                log.warn("sql脚本中特殊字符【{}】已被过滤", str);
            }
        }
        return value;
    }

    private static Set<String> parseSqlNode(SqlNode sqlNode) throws Exception {
        Set<String> parameterKeys = new HashSet<String>();
        if (sqlNode instanceof TextSqlNode) {
            Field textField = (Field) cache.get("text");
            if (null == textField) {
                textField = ReflectionUtils.findField(sqlNode.getClass(), "text");
                textField.setAccessible(true);
                cache.put("text", textField);
            }
            String text = (String) textField.get(sqlNode);
            String[] texts = StringUtils.substringsBetween(text, "${", "}");
            parameterKeys.addAll(Arrays.asList(texts));
            return parameterKeys;
        }

        Field contentsField = null, defaultSqlNodeField = null, ifSqlNodesField = null;
        if (sqlNode instanceof ChooseSqlNode) {
            defaultSqlNodeField = (Field) cache.get("defaultSqlNode");
            if (null == defaultSqlNodeField) {
                defaultSqlNodeField = ReflectionUtils.findField(sqlNode.getClass(), "defaultSqlNode");
                defaultSqlNodeField.setAccessible(true);
                cache.put("defaultSqlNode", defaultSqlNodeField);
            }

            ifSqlNodesField = (Field) cache.get("ifSqlNodes");
            if (null == ifSqlNodesField) {
                ifSqlNodesField = ReflectionUtils.findField(sqlNode.getClass(), "ifSqlNodes");
                ifSqlNodesField.setAccessible(true);
                cache.put("ifSqlNodes", ifSqlNodesField);
            }
        } else if (!(sqlNode instanceof StaticTextSqlNode)) {
            contentsField = ReflectionUtils.findField(sqlNode.getClass(), "contents");// 不能缓存，无法判断sqlNode类型
            if (null != contentsField) {
                contentsField.setAccessible(true);
            }
        }

        if (null != contentsField || null != defaultSqlNodeField || null != ifSqlNodesField) {
            if (null != defaultSqlNodeField) {
                Object contents = defaultSqlNodeField.get(sqlNode);
                if (null != contents) {
                    parameterKeys.addAll(parseSqlNode((SqlNode) contents));
                }
            }

            Object contents = null;
            if (null != contentsField) {
                contents = contentsField.get(sqlNode);
            } else if (null != ifSqlNodesField) {
                contents = ifSqlNodesField.get(sqlNode);
            }
            if (contents instanceof Collection) {
                List<SqlNode> mixedSqlNode = (List<SqlNode>) contents;
                for (SqlNode sql : mixedSqlNode) {
                    parameterKeys.addAll(parseSqlNode(sql));
                }
            } else if (null != contents) {
                parameterKeys.addAll(parseSqlNode((SqlNode) contents));
            }
        }

        return parameterKeys;
    }
}
