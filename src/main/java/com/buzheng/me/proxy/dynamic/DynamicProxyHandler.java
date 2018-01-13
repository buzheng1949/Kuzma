package com.buzheng.me.proxy.dynamic;

import com.buzheng.me.proxy.staticproxy.ProxySubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by buzheng on 18/1/13.
 * 通用的动态代理类
 */
public class DynamicProxyHandler implements InvocationHandler {

    private Logger logger = LoggerFactory.getLogger(DynamicProxyHandler.class);

    private Object target;

    public DynamicProxyHandler(Object target) {
        this.target = target;
    }

    public <T> T getProxy() {
        return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.error("proxy dynamic start");
        method.invoke(target, args);
        logger.error("proxy dynamic end");
        return null;
    }
}
