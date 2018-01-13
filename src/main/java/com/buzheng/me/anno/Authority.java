package com.buzheng.me.anno;

import com.buzheng.me.common.OperatorType;

import java.lang.annotation.*;

/**
 * Created by buzheng on 18/1/13.
 * 需要权限校验
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authority {


}
