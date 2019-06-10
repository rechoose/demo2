package cn.gw.demo2.lock;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SingleTaskLock {

    long expire() default 10;//默认锁过期时间10秒
}
