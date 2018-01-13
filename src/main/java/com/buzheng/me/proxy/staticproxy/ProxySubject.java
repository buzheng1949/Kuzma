package com.buzheng.me.proxy.staticproxy;

import com.buzheng.me.proxy.common.RealSubject;
import com.buzheng.me.proxy.common.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by buzheng on 18/1/13.
 * 静态代理
 */
public class ProxySubject implements Subject {

    private Logger logger = LoggerFactory.getLogger(ProxySubject.class);


    private Subject realSubject;

    public ProxySubject(Subject realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public void exec() {
        logger.error("proxy start");
        realSubject.exec();
        logger.error("proxy end");
    }
}
