package cn.gw.demo2.interceptor;

import java.lang.annotation.*;

/**
 * 用于测定方法的执行时间
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogConsumedTime {
}
