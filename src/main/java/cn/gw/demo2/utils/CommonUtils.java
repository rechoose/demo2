package cn.gw.demo2.utils;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonUtils {

    /**
     * 去除字符串中所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
     *
     * @param s
     * @return
     */
    public static String removeAllBlank(String s) {
        String result = "";
        if (null != s && !"".equals(s)) {
            result = s.replaceAll("[　*| *| *|\\s*]*", "");
        }
        return result;
    }

    public static String removeYinHao(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return s.replaceAll("\"", "");
    }

    public static String getRandomUpperCaseLetter(int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result += (char) ('A' + Math.random() * ('Z' - 'A' + 1));
        }
        return result;
    }

    public static String md5Encode(String msg) throws Exception {

        byte[] msgBytes = msg.getBytes("utf-8");
        /**
         * 声明使用Md5算法,获得MessaDigest对象
         */
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        /**
         * 使用指定的字节更新摘要
         */
        md5.update(msgBytes);
        /**
         * 完成哈希计算,获得密文
         */
        byte[] digest = md5.digest();
        /**
         * 以上两行代码等同于 byte[] digest = md5.digest(msgBytes);
         */
        return bytesToHexString(digest);
    }

    /**
     * 将byte数组转化为16进制字符串形式
     *
     * @param bys
     * @return
     */
    public static String bytesToHexString(byte[] bys) {
        StringBuffer hexVal = new StringBuffer();
        int val = 0;
        for (int i = 0; i < bys.length; i++) {
            //将byte转化为int  如果byte是一个负数就必须要和16进制的0xff做一次与运算
            val = ((int) bys[i]) & 0xff;
            if (val < 16) {
                hexVal.append("0");
            }
            hexVal.append(Integer.toHexString(val));
        }

        return hexVal.toString();

    }

    /**
     * @param filePath        例如: f:\test.jpg  或  f:\test  均可
     * @param defaultFileName 例如: test.jpg
     * @return
     * @throws Exception
     */
    //确认该路径存在,如果是目录就拼上文件,如果是文件就保证路径存在
    public static File getFile(String filePath, String defaultFileName) throws Exception {
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (file.exists()) {//存在
            if (file.isDirectory()) {//存在是目录
                String tempFile = filePath + File.separator + defaultFileName;
                return new File(tempFile);
            } else {//存在是文件
                return file;
            }
        } else {//不存在
            String lastPart = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
            if (lastPart.contains(".")) {//是文件??待完善,临时处理
                String dir = filePath.substring(0, filePath.lastIndexOf(File.separator));
                File dirF = new File(dir);
                dirF.mkdirs();//创建路径
                return new File(filePath);
            } else {//是目录
                file.mkdirs();
                String tempFile = filePath + File.separator + defaultFileName;
                return new File(tempFile);
            }
        }
    }

    //确认该路径存在
    public static String getPath(String filePath) throws Exception {
        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }
        File file = new File(filePath);
        if (file.exists()) {//存在
            if (file.isDirectory()) {//存在是目录
                return filePath;
            } else {//存在是文件
                return filePath.substring(0, filePath.lastIndexOf(File.separator));
            }
        } else {//不存在
            String lastPart = filePath.substring(filePath.lastIndexOf(File.separator));
            if (lastPart.contains(".")) {//是文件
                String dir = filePath.substring(0, filePath.lastIndexOf(File.separator));
                File dirF = new File(dir);
                dirF.mkdirs();//创建路径
                return dir;
            } else {
                file.mkdirs();
                return filePath;
            }
        }
    }

    //反射获取所有的属性
    public static Field[] getAllFields(Object object) {
        return getAllFields(object.getClass());
    }

    public static Field[] getAllFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    //超过1000的list转为多个小于1000的list
    public static List<List<String>> split1000(List<String> listMoreThen1000) {
        List<List<String>> multiListEachLessThen1000 = new ArrayList<>();
        if (CollectionUtils.isEmpty(listMoreThen1000)) {
            return multiListEachLessThen1000;
        }
        int allCount = listMoreThen1000.size();
        int num = allCount / 999;
        for (int i = 0; i <= num; i++) {
            List<String> strings = new ArrayList<>();
            if (i >= num) {
                strings = listMoreThen1000.subList(i * 999, allCount);
            } else {
                strings = listMoreThen1000.subList(i * 999, (i + 1) * 999);//  0~998
            }
            multiListEachLessThen1000.add(strings);
        }
        return multiListEachLessThen1000;
    }
}
