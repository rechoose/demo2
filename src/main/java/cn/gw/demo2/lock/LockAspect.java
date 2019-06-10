package cn.gw.demo2.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LockAspect {

    @Autowired
    private LockService lockService;

    @Around("@annotation(cn.gw.demo2.lock.SingleTaskLock)")
    public void around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        String lockKey = method.getDeclaringClass().getName() + "." + method.getName();
        SingleTaskLock annotation = method.getAnnotation(SingleTaskLock.class);
        boolean lock = lockService.lock(lockKey, annotation.expire());
        if (!lock) return;
        try {
            point.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            lockService.unlock(lockKey);
        }
    }

}
