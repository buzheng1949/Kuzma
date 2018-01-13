package com.buzheng.me.anno;

import com.buzheng.me.common.OperatorType;

import java.lang.annotation.*;

/**
 * Created by buzheng on 18/1/13.
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Operator {
    /**
     * 操作类型
     *
     * @return
     */
    OperatorType value() default OperatorType.SCAN;
}
