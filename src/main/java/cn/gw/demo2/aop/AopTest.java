package cn.gw.demo2.aop;

import com.google.common.collect.Lists;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class AopTest {

    @Pointcut("execution(* *..MapLeftMenuController.getCarGateInfoByNum(..))")
    public void testPoint() {
    }

    @Pointcut("execution(* *..MapLeftMenuController.getPoliceByNameLike*(..))")
    public void test1Point() {
    }

    @Around("testPoint()||test1Point()")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable {
        Map<String, Object> fieldsName = getFieldsName(joinPoint);
        Map<String, Object> fieldsName1 = getFieldsName1(joinPoint);
        return Lists.newArrayList(fieldsName, fieldsName1);
    }

    //aop获取方法的入参值
    private Map<String, Object> getFieldsName(ProceedingJoinPoint joinPoint) throws Exception {
        Map<String, Object> map = new HashMap<>();

        String clazzName = joinPoint.getTarget().getClass().getName();
        Class<? extends AopTest> cls = this.getClass();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            throw new NotFoundException("aop无法获取方法{" + clazzName + "." + methodName + "}入参值");
        } else {
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < cm.getParameterTypes().length; i++) {
                map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名
            }
            return map;
        }
    }

    //aop获取方法的入参值
    private Map<String, Object> getFieldsName1(ProceedingJoinPoint joinPoint) throws Exception {
        HashMap<String, Object> result = new HashMap<>();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < parameterNames.length; i++) {
            result.put(parameterNames[i], args[i]);
        }
        return result;
    }
}
