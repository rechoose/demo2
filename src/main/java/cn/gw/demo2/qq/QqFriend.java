package cn.gw.demo2.qq;

import cn.gw.demo2.utils.SerializeUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class QqFriend {

    private String filePath;
    private Map<String, String> cookies;

    public QqFriend(String cookesFilePath) {
        filePath = cookesFilePath;
        cookies = this.readCookes();
    }

    //用fidder抓包,权限管理里面
    private Map<String, String> readCookes() {
        HashMap<String, String> map = Maps.newHashMap();
        try {
            BufferedReader reader = Files.newReader(new File(filePath), Charset.forName("utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("=");
                if (split.length > 1) {
                    map.put(split[0], split[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public List<SimpleUser> getAllFriendSimpleInfo() {
        List<SimpleUser> allUser = new ArrayList<>();
        String qq = cookies.get("o_cookie");
        String pskey = cookies.get("p_skey");

        String g_tk = getTk(pskey);

        for (int i = 0; ; ) {
            List<SimpleUser> users = null;
            try {
                Document document = Jsoup.connect("https://h5.qzone.qq.com/proxy/domain/base.qzone.qq.com/cgi-bin/right/get_entryuinlist.cgi?uin=" + qq + "&fupdate=1&action=1&offset=" + i + "&g_tk=" + g_tk).cookies(cookies).header("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36").timeout(10000).ignoreContentType(true).get();
                String strJson = document.getElementsByTag("body").text().replace("_Callback(", "").replace(");", "");
                JSONObject jsonObject = new JSONObject(strJson);
                String jsonData = ((JSONObject) jsonObject.get("data")).get("uinlist").toString();
                users = SerializeUtil.toObject(jsonData, new TypeReference<List<SimpleUser>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (users == null || users.size() == 0) {
                break;
            }
            allUser.addAll(users);
            i += 50;
        }
        return allUser;
    }

    public UserDto getFridenDetail(String qq) {
        UserDto user = new UserDto();
        Map<String, String> data = new HashMap<>();
        //注意这里，获得信息是用的skey 而获得全部好友使用的为p_skey
        String skey = cookies.get("skey");
        String g_tk = getTk(skey);

        data.put("keyword", qq);
        data.put("ldw", g_tk);

        Document document;
        try {
            document = Jsoup.connect("http://cgi.find.qq.com/qqfind/buddy/search_v3").cookies(cookies).header("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36").ignoreContentType(true).data(data).timeout(10000).post();
            String allJson = document.getElementsByTag("body").text();
            System.out.println(allJson);
            JSONObject result = (JSONObject) new JSONObject(allJson).get("result");
            JSONObject buddy = (JSONObject) result.get("buddy");
            JSONArray jsonArray = buddy.getJSONArray("info_list");

            JSONObject dataJson = (JSONObject) jsonArray.get(0);
            user = SerializeUtil.toObject(dataJson.toString(), UserDto.class);
            JSONObject birthdayJson = (JSONObject) dataJson.get("birthday");
            String year = birthdayJson.get("year").toString();
            String month = birthdayJson.get("month").toString();
            String day = birthdayJson.get("day").toString();
            String birthday = year + "-" + month + "-" + day;
//            user.setBirthday(birthday);

            Calendar calendar = Calendar.getInstance();
            int now = calendar.get(Calendar.YEAR);

            if (year != null && year.length() > 0) {
                int age = now - Integer.parseInt(year);
                user.setAge(age);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private String getTk(String skey) {
        int hash = 5381;
        for (int i = 0, len = skey.length(); i < len; ++i) {
            hash += (hash << 5) + (int) (char) skey.charAt(i);
        }
        return (hash & 0x7fffffff) + "";
    }

    public static void main(String[] args) {
        QqFriend qqFriend = new QqFriend("E:\\test\\demo2\\src\\main\\resources\\qq\\qqCookes");
        List<SimpleUser> allFriendSimpleInfo = qqFriend.getAllFriendSimpleInfo();
        int i = new Random().nextInt(allFriendSimpleInfo.size());
        UserDto fridenDetail = qqFriend.getFridenDetail(allFriendSimpleInfo.get(i).getData());
        UserDto fridenDetail1 = qqFriend.getFridenDetail("125668393");
        System.out.println(fridenDetail.toString());
        System.out.println(fridenDetail1.toString());

    }

}
