package com.buzheng.me.ast;

import java.lang.annotation.*;

/**
 * Created by buzheng on 18/1/23.
 * 自定义一个getter注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface BuZhengGetter {

}
