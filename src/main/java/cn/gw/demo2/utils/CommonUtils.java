package cn.gw.demo2.utils;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
            fieldList.addAll(Lists.newArrayList(clazz.getDeclaredFields()));
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

      //自动生成名字（中文）
    public static String randomGBKJianHan(int len) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39)));//获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93)));//获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                sb.append(new String(b, "GBk"));//转成中文
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String s ="";
        try {
             s = new String(getUTF8BytesFromGBKString(sb.toString()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    //生成随机用户名，数字和字母组成,
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    //gbk转为utf8
    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }


}
