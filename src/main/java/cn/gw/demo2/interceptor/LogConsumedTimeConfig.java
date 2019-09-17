package cn.gw.demo2.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class LogConsumedTimeConfig {

    //切点通知
    @Bean
    public PointcutAdvisor registerMethod() {
        DefaultBeanFactoryPointcutAdvisor result = new DefaultBeanFactoryPointcutAdvisor();
        result.setAdvice(new LogConsumedTimeAdvice());
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, LogConsumedTime.class);
        result.setPointcut(pointcut);
        return result;
    }

    //增强方法
    //方法拦截器
    class LogConsumedTimeAdvice implements MethodInterceptor {
        private final Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            long startTime = System.currentTimeMillis();
            Object result = methodInvocation.proceed();//执行拦截的方法
            long endTime = System.currentTimeMillis();
            Method method = methodInvocation.getMethod();
            String className = method.getDeclaringClass().getName();
            String methodName = method.getName();
            logger.info("{}类{}方法成功执行， 耗时{}毫秒", className, methodName, endTime - startTime);
            return result;
        }
    }


}
