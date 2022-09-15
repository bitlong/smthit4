package cn.smthit.v4.common.lang.log;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/17  9:12
 */
@Data
@Aspect
@Order(1)
@Slf4j
public class MethodLogAspect {
    private boolean output = true;
    private MethodLogListener listener;
    private UserContextParser userContextParser;

    @Pointcut("@annotation(com.sgai.common.lang.log.MethodLogger)")
    public void methodLogger() {
    }

    @Around("methodLogger() && @annotation(logger)")
    public Object around(ProceedingJoinPoint joinPoint, MethodLogger logger) throws Throwable {
        Long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();


            MethodLog methodInvokeLog = new MethodLog();
            methodInvokeLog.setModule(logger.module());
            methodInvokeLog.setOperate(logger.operate());
            methodInvokeLog.setInterfaceName(joinPoint.getSignature().getName());
            methodInvokeLog.setReturnData(result);
            methodInvokeLog.setCostTime(System.currentTimeMillis() - startTime);

            if(userContextParser != null) {
                UserInfo ui = userContextParser.getUserInfo();
                methodInvokeLog.setUserInfo(ui);
            }

            if(output) {
                log.info("Method Invoke: " + methodInvokeLog.toString());
            }

            if(listener != null) {
                listener.onLog(methodInvokeLog);
            }

            return result;
        } catch (Throwable throwable) {
            if(listener != null) {
                listener.onException(throwable);
            }
            throw throwable;
        }
    }
}