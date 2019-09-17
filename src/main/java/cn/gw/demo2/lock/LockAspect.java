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
 * 切面,被@SingleTaskLock注解的方法,在多实例的时候只有一个实例执行
 */
@Aspect
@Component
public class LockAspect {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private LockService lockService;

    @Around("@annotation(SingleTaskLock)")
    public void around(ProceedingJoinPoint point) throws Throwable {
        String threadName = Thread.currentThread().getName();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        String lockKey = method.getDeclaringClass().getName() + "." + method.getName();
        SingleTaskLock annotation = method.getAnnotation(SingleTaskLock.class);
        boolean lock = lockService.lock(lockKey, annotation.expire());
        if (!lock) {
            log.error("can not get the lock:{}, jump over", lockKey);
            return;
        }
        try {
            log.info("get the lock:{}, run...", lockKey);
            point.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            lockService.unlock(lockKey);
            log.info("unlock:{}, can be restart", lockKey);
        }
    }

}
