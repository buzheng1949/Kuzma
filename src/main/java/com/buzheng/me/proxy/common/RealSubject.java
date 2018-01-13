package com.buzheng.me.proxy.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by buzheng on 18/1/13.
 */
public class RealSubject implements Subject {
    private Logger logger = LoggerFactory.getLogger(RealSubject.class);

    @Override
    public void exec() {
        logger.error("real subject is called");
    }
}
