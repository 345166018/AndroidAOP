package com.hongx.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: yaichain18
 * @create: 2019/6/30 10:57 AM
 */

/**
 * 用来表示性能监控
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BehaviorTrace {
    String value();
}
