package cn.gw.demo2.lock;

import java.lang.annotation.*;

/**
 * 主要用于多实例的定时任务,要求任务只执行一次
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SingleTaskLock {

    long expire() default 10;//默认锁过期时间10秒
}
