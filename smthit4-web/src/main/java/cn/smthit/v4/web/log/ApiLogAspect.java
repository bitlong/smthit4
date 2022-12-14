package cn.smthit.v4.web.log;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import cn.smthit.v4.common.lang.log.UserInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/17  9:41
 */
@Data
@Aspect
@Order(1)
@Slf4j
public class ApiLogAspect {
    private boolean output = false;
    private ApiLogListenter listener;
    private UserWebContextParser userWebContextParser;

    @Pointcut("@annotation(cn.smthit.v4.web.log.ApiLogger)")
    public void apiLogger() {}

    @Before("apiLogger()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @AfterReturning(value = "apiLogger()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
    }

    @Around("apiLogger()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录请求信息
        ApiLog apiLog = new ApiLog();

        //前面是前置通知，后面是后置通知
        try {
            Object result = joinPoint.proceed();


            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            long endTime = System.currentTimeMillis();
            String urlStr = request.getRequestURL().toString();

            apiLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
            apiLog.setIp(request.getRemoteUser());
            apiLog.setMethod(request.getMethod());

            apiLog.setParameter(getParameter(method, joinPoint.getArgs()));

            apiLog.setResult(result);
            apiLog.setSpendTime((int) (endTime - startTime));
            apiLog.setStartTime(startTime);
            apiLog.setUri(request.getRequestURI());
            apiLog.setUrl(request.getRequestURL().toString());

            if(userWebContextParser != null) {
                UserInfo userInfo = userWebContextParser.getUserInfo(request);
                if(userInfo != null) {
                    StringJoiner sj = new StringJoiner("|");
                    if(userInfo.getUserId() != null) {
                        sj.add("user_id=" + userInfo.getUserId() + "");
                    }
                    if(userInfo.getName() != null) {
                        sj.add("name=" + userInfo.getName());
                    }
                    if(userInfo.getUsername() != null) {
                        sj.add("username=" +userInfo.getUsername());
                    }
                }
                apiLog.setUsername(userInfo.getUserId() + ":" + userInfo.getName());
            }

            if(output) {
                log.info("{}", JSONUtil.parse(apiLog));
            }

            if(listener != null) {
                listener.onLog(apiLog);
            }

            return result;

        } catch(Throwable throwable) {
            if(listener != null) {
                listener.onException(throwable);
            }
            throw throwable;
        }
    }

    @Nullable
    private Object getParameter(Method method, Object[] args) {

        List<Object> argumentList = new ArrayList<>();

        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {

            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argumentList.add(args[i]);
            }

            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argumentList.add(map);
            }
        }

        if (argumentList.size() == 0) {
            return null;
        } else if (argumentList.size() == 1) {
            return argumentList.get(0);
        } else {
            return argumentList;
        }
    }
}