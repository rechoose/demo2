package cn.gw.demo2.utils;

import org.springframework.util.StringUtils;

import java.security.MessageDigest;

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
}
