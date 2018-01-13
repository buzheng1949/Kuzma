package com.buzheng.me.proxy.cglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by buzheng on 18/1/13.
 */
public class CGLibMethodInteceptor implements MethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(CGLibMethodInteceptor.class);

    private Object target;

    public CGLibMethodInteceptor(Object target) {
        this.target = target;
    }

    /**
     * 获取代理对象
     * @param <T>
     * @return
     */
    public <T> T getProxy(){
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(this);
        enhancer.setSuperclass(target.getClass());
        return (T)enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        logger.error("cglib start");
        Object result = methodProxy.invokeSuper(o, args);
        logger.error("cglib end");
        return result;
    }
}
