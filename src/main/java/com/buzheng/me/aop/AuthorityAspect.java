package com.buzheng.me.aop;

import com.buzheng.me.holder.UserHolder;
import com.buzheng.me.service.impl.StudentServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by buzheng on 18/1/13.
 * 切面
 */
@Aspect
@Component
public class AuthorityAspect {

    private Logger logger = LoggerFactory.getLogger(AuthorityAspect.class);

    //切点 使用注解
    @Pointcut("@annotation(com.buzheng.me.anno.Authority)")
    public void matchAnnotation() {
    }

    //切点 使用within 匹配包或者类
    @Pointcut("within(com.buzheng.me.service.impl.StudentServiceImpl)")
    public void matchClass() {
    }

    //切点 ..表示子类 * 表示所有
    @Pointcut("within(com.buzheng.me.service..*)")
    public void matchPackage() {
    }

    //切点 使用bean的名称进行匹配
    @Pointcut("bean(*Service)")
    public void matchBeanName() {
    }

    //切点 public 修饰符 * 返回值 com.buzheng.me.*Service 匹配类 *(..//这里是参数) 类下面所有方法
    @Pointcut("execution(public void  com.buzheng.me.service.*Service.*(..))")
    public void matchMethod() {
    }

    @Before("matchAnnotation()")
    public void authority() {
        if (!UserHolder.get().equals("admin")) {
            throw new RuntimeException("sorry you have no permission");
        }
    }

}

