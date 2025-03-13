package cn.smthit.v4.web.sql;

import cn.hutool.crypto.SecureUtil;

import cn.smthit.v4.common.lang.sql.SqlInjectKit;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * WEB参数签名和检查
 * @author bean
 * @date 2023/9/26
 */
@Slf4j
public class SqlInjectWebkit {

    /**
     * 针对sql进行额外的sign签名校验（增加安全机制）
     * @param dictCode:
     * @param sign:
     * @param request:
     * @Return: void
     */
    public static void checkDictTableSign(String dictCode, String sign, HttpServletRequest request) {
        //表字典SQL注入漏洞,签名校验

        String accessToken = request.getHeader("X-Access-Token");

        String signStr = dictCode + SqlInjectKit.TABLE_DICT_SIGN_SALT + accessToken;
        String javaSign = SecureUtil.md5(signStr);
        if (!javaSign.equals(sign)) {
            log.info("SQL注入漏洞签名校验失败 ：" + sign + "!=" + javaSign+ ",dictCode=" + dictCode);
            throw new RuntimeException("无权限访问！");
        }

        log.info("SQL注入漏洞签名校验成功！sign=" + sign + ",dictCode=" + dictCode);
    }
}
