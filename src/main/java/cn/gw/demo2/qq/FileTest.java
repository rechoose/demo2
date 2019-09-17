package cn.gw.demo2.qq;

import cn.gw.demo2.utils.SerializeUtil;
import cn.gw.demo2.utils.XLSXCovertCSVReader;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FileTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) throws Exception {
       /* FileTest fileTest = new FileTest();
        ArrayList<Consumer> comsumers = fileTest.get();
        XLSXCovertCSVReader.writeTo2007Excel(comsumers, Consumer.class, "C:\\Users\\Administrator\\Desktop\\123.xlsx", "111");
        System.out.println();*/


        Consumer comsumer = new Consumer();
        comsumer.setName("jack");
        comsumer.setNum("1233");

        Map map1 = SerializeUtil.pojoToMap(comsumer, Map.class);

        Consumer consumer = SerializeUtil.mapToPojo(map1, Consumer.class);

        UserDto userDto = new UserDto();
        userDto.setAllow("322");
        Birthday birthday = new Birthday();
        birthday.setYear("2290");
        userDto.setBirthday(birthday);

        Map map2 = SerializeUtil.pojoToMap(userDto, Map.class);

        UserDto userDto2 = SerializeUtil.mapToPojo(map2, UserDto.class);

        System.out.println();

    }

    private ArrayList<Consumer> get() throws IOException {
        String filePath = "C:\\Users\\Administrator\\Desktop\\222.txt";
        BufferedReader reader = Files.newReader(new File(filePath), Charset.forName("utf-8"));

        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
//        String sp = format.format(date);
        String sp = "2019-09-11";
        Iterable<String> split = Splitter.on(sp).omitEmptyStrings().trimResults().split(sb.toString());
        ArrayList<String> list = Lists.newArrayList(split);
        list.remove(0);
        ArrayList<Consumer> comsumers = new ArrayList<>();
        for (String s : list) {
            try {
                if (!(s.contains("下一位") || s.contains("余额") || s.contains("餐"))){
                    log.error(s);
                    continue;
                }
                String str = "";
                String num = "";
                if (s.contains("(")) {
                    str = s.substring(s.indexOf("(") + 1);
                    num = str.substring(0, str.indexOf(")"));
                    str = str.substring(str.indexOf(")") + 1);
                }
                if (s.contains("<")) {
                    str = s.substring(s.indexOf("<") + 1);
                    num = str.substring(0, str.indexOf(">"));
                    str = str.substring(str.indexOf(">") + 1);
                }
                String firstSigure = getFirstSigure(str);
                int i = str.indexOf(firstSigure);
                String temp = str.substring(0, i);
                String name = temp.replace(",", "").replace(" ", "").trim();
                String s2 = str.substring(i + firstSigure.length());
                String secondSigure = getFirstSigure(s2);
                String[] split2 = s2.split(secondSigure);
                String nextName = split2[1].replaceAll(",", "").replaceAll("，", "").replaceAll(" ", "").trim();
                Consumer comsumer = new Consumer();
                comsumer.setName(name.replace("余额", "").replace("余", ""));
                comsumer.setNum(num);
                comsumer.setTime("");
                Double v = Double.valueOf(firstSigure) * 100;
                comsumer.setCost(v.intValue());
                Double v2 = Double.valueOf(secondSigure) * 100;
                comsumer.setSurplus(v2.intValue());
                comsumer.setNextName(nextName.replace("下一位", "").replace("下位", "").replace("下", ""));
                comsumers.add(comsumer);
            } catch (Exception e) {
                log.error(s);
            }
        }
        return comsumers;
    }

    public static String getFirstSigure(String str) {
        char[] sb = str.toCharArray();
        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i < sb.length; i++) {
            char aChar = sb[i];
            int value = aChar - '0';
            if ((value >= 0 && value <= 9) || value == -2) {
                if (startIndex == -1) {
                    startIndex = i;
                    endIndex = i;
                } else {
                    endIndex++;
                }
            } else {
                if (startIndex >= 0) {
                    break;
                }
            }
        }
        if (startIndex == -1) {
            return "";
        }
        return str.substring(startIndex, endIndex + 1);
    }


}
