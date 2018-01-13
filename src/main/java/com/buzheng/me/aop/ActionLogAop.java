package com.buzheng.me.aop;

import com.buzheng.me.anno.Authority;
import com.buzheng.me.anno.Operator;
import com.buzheng.me.domain.ActionLog;
import com.buzheng.me.holder.UserHolder;
import com.buzheng.me.mapper.ActionLogMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by buzheng on 18/1/13.
 */
@Aspect
@Component
public class ActionLogAop {

    private Logger logger = LoggerFactory.getLogger(ActionLogAop.class);

    @Autowired
    private ActionLogMapper actionLogMapper;

    @Pointcut("@annotation(com.buzheng.me.anno.Operator)")
    public void log() {
    }

    @Around("log()")
    public Object record(ProceedingJoinPoint proceedingJoinPoint) {
        Object res = null;
        try {
            res = proceedingJoinPoint.proceed();
            Operator operator = getActionLogAnno(proceedingJoinPoint);
            Integer type = operator.value().getType();
            String desc = operator.value().getDesc();
            Long nowTime = System.currentTimeMillis() / 1000;
            Integer userId = UserHolder.get().hashCode();
            ActionLog actionLog = new ActionLog();
            actionLog.setUserId(userId);
            actionLog.setTime(nowTime.intValue());
            actionLog.setType(type);
            actionLog.setTradeItemId(desc.hashCode());
            Integer result = actionLogMapper.insert(actionLog);
            if (result > 1) {
                logger.info("the log is insert success");
            }

        } catch (Throwable e) {
            logger.error("use aop to record log failed", e);
        }
        return res;

    }

    /**
     * 获取ActionLog Annotion 注解
     */
    private Operator getActionLogAnno(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Operator.class);
        }
        return null;
    }
}
