package cn.gw.demo2.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Map;

public class SerializeUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static ObjectMapper setDateFormat(String pattern) {
        objectMapper.setDateFormat(new SimpleDateFormat(pattern));
        return objectMapper;
    }

    public static String toJson(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T mapToPojo(Map fromValue, Class<T> toValue) {
        return objectMapper.convertValue(fromValue, toValue);
    }

    public static <T> T mapToPojo(Map fromValue, TypeReference<T> toValue) {
        return objectMapper.convertValue(fromValue, toValue);
    }

    public static <T> Map pojoToMap(T fromValue, Class<Map> toValue) {
        return objectMapper.convertValue(fromValue, toValue);
    }

    public static <T> Map pojoToMap(T fromValue, TypeReference<Map> toValue) {
        return objectMapper.convertValue(fromValue, toValue);
    }

    public static <T> T toObject(String jsonStr, Class<T> valueType) throws Exception {
        return objectMapper.readValue(jsonStr, valueType);
    }

    public static <T> T toObject(String jsonStr, TypeReference<T> valueType) throws Exception {
        return objectMapper.readValue(jsonStr, valueType);
    }

}
