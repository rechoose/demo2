package cn.gw.demo2.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class EmailControllerAop {

    @Pointcut("execution(* *..EmailController.save*(..))")
    public void savePoint() {
    }

    @Pointcut("execution(* *..EmailController.update*(..))")
    public void updatePoint() {
    }

    @Pointcut("execution(* *..EmailController.delete*(..))")
    public void deletePoint() {
    }

    @Around("savePoint()||updatePoint()||deletePoint()")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("切");
        return joinPoint.proceed();
    }


}
