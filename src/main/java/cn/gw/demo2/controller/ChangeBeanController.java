package cn.gw.demo2.controller;

import cn.gw.demo2.config.SpringUtils;
import cn.gw.demo2.utils.CommonUtils;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;


@RestController
@RequestMapping("/bean")
public class ChangeBeanController {


    //自主选择服务提供方
    @ApiOperation("自主选择服务提供方")
    @GetMapping("/change")
    public String config(@RequestParam String beanField) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        //beanField 标准: com.jp.bsm.config.GodConfig_name_iiiii, 全限定名_属性名_属性值
        if (StringUtils.isEmpty(beanField) || !beanField.contains("_")) {
            throw new RuntimeException("param : {" + beanField + "} is illegal");
        }
        Iterable<String> split = Splitter.on("_").omitEmptyStrings().trimResults().split(beanField);
        ArrayList<String> strings = Lists.newArrayList(split);
        String beanPathName = strings.get(0);
        String beanFieldName = strings.get(1);
        String beanFieldValue = strings.get(2);

        Class<?> aClass = Class.forName(beanPathName);

        Object bean = SpringUtils.getBean(aClass);

        Field field = aClass.getDeclaredField(beanFieldName);

        field.setAccessible(true);

        String oldValue = field.get(bean).toString();

        String typeName = field.getType().getTypeName();

        if (typeName.endsWith("int") || typeName.endsWith("Integer")) {
            field.set(bean, Integer.valueOf(beanFieldValue));
        } else if (typeName.endsWith("boolean") || typeName.endsWith("Boolean")) {
            field.set(bean, Boolean.valueOf(beanFieldValue));
        } else if (typeName.endsWith("byte") || typeName.endsWith("Byte")) {
            field.set(bean, Byte.valueOf(beanFieldValue));
        } else if (typeName.endsWith("double") || typeName.endsWith("Double")) {
            field.set(bean, Double.valueOf(beanFieldValue));
        } else if (typeName.endsWith("float") || typeName.endsWith("Float")) {
            field.set(bean, Float.valueOf(beanFieldValue));
        } else if (typeName.endsWith("long") || typeName.endsWith("Long")) {
            field.set(bean, Long.valueOf(beanFieldValue));
        } else if (typeName.endsWith("short") || typeName.endsWith("Short")) {
            field.set(bean, Short.valueOf(beanFieldValue));
        } else if (typeName.endsWith("String")) {
            field.set(bean, beanFieldValue);
        } else {
            field.set(bean, beanFieldValue.charAt(0));
        }
        return "bean名:" + beanPathName + "的属性" + beanFieldName + ",值从" + oldValue + "修改为:" + field.get(bean).toString();
    }

}
