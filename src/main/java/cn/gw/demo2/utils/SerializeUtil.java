package cn.gw.demo2.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

public class SerializeUtil {

    private static ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//没有对应的属性,不抛异常

    public static ObjectMapper setDateFormat(String pattern) {
        objectMapper.setDateFormat(new SimpleDateFormat(pattern));
        return objectMapper;
    }

    public static String toJson(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    //简单对象
    public static <T> T toObject(String jsonStr, Class<T> valueType) throws Exception {
        return objectMapper.readValue(jsonStr, valueType);
    }

    //复杂对象,可代替其他方法
    public static <T> T toObject(String jsonStr, TypeReference<T> valueType) throws Exception {
        //关键
        return objectMapper.readValue(jsonStr, valueType);
    }

}
