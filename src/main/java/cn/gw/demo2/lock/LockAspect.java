package cn.gw.demo2.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切面,被@SingleTaskLock注解的方法,在多实例的时候只会执行一次
 */
@Aspect
@Component
public class LockAspect {

    private Logger log = LoggerFactory.getLogger(LockAspect.class);

    @Autowired
    private LockService lockService;

    @Around("@annotation(cn.gw.demo2.lock.SingleTaskLock)")
    public void around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        String lockKey = method.getDeclaringClass().getName() + "." + method.getName();
        SingleTaskLock annotation = method.getAnnotation(SingleTaskLock.class);
        boolean lock = lockService.lock(lockKey, annotation.expire());
        if (!lock) {
            log.error("{}:can not get the lock, jump over", Thread.currentThread().getName());
            return;
        }
        try {
            log.debug("{}:get the lock, run...", Thread.currentThread().getName());
            point.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            log.debug("{}:unlock, can be restart", Thread.currentThread().getName());
            lockService.unlock(lockKey);
        }
    }

}
