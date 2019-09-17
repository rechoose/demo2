package cn.gw.demo2.reflect1;

import cn.gw.demo2.utils.CommonUtils;

import java.lang.reflect.*;

public class Method1 {

    //获取类的属性
    public static void getFields(Object object) {
        Class<?> aClass = object.getClass();
        //获取泛型的class对象
        Class<?> entityClass = (Class<?>) ((ParameterizedType) object.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        Method[] declaredMethods = aClass.getDeclaredMethods();//获取自己的public和private方法
        Method[] methods = aClass.getMethods();//获取自己和父类的public方法

        Field[] declaredFields = aClass.getDeclaredFields();//获取自己的属性

        for (Field declaredField : declaredFields) {
            System.out.println("getDeclaredFields: " + declaredField.getName());
            if ("id".equalsIgnoreCase(declaredField.getName())) {
                try {
                    declaredField.setAccessible(true);
                    System.out.println(declaredField.get(object));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        for (Method method : declaredMethods) {
            System.out.println("getDeclaredMethods: " + method.getName());
            if ("getid".equalsIgnoreCase(method.getName())) {
                try {
                    Object invoke = method.invoke(object);
                    System.out.println(invoke);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }


        for (Method method : methods) {
            System.out.println("getMethods: " + method.getName());
        }
    }

    public static void main(String[] args) {
        ObjectTest objectTest = new ObjectTest();
        objectTest.setId("1");
        getFields(objectTest);
        Field[] fields = CommonUtils.getAllFields(ObjectTest.class);
        for (Field field : fields) {
            System.out.println("1:1: "+field.getName());
        }
        System.out.println(fields.length);
    }




}
