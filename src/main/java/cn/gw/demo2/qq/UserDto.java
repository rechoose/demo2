package cn.gw.demo2.qq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private String face;
    private int age;
    private Birthday birthday;
    private String phone;
    private String gender_id;
    private String allow;
    private String extflag;
    private String college;
    private String uin;
    private String lnick;
    private String cft_flag;
    private String h_zone;
    private String reg_type;
    private String city;
    private String h_city;
    private String city_id;
    private String personal;
    private String shengxiao;
    private String token;
    private String province;
    private String gender;
    private String s_flag;
    private String occupation;
    private String zone_id;
    private String province_id;
    private String country_id;
    private String constel;
    private String blood;
    private String url;
    private String stat;
    private String homepage;
    private String country;
    private String flag;
    private String h_country;
    private String nick;
    private String email;
    private String gps_flag;
    private String h_province;
    private String mobile;

    @Override
    public String toString() {
        return "UserDto{" +
                "nick='" + nick + '\'' +
                ",age=" + age +
                ", uin='" + uin + '\'' +
                ", mobile='" + mobile + '\'' +
                ", phone='" + phone + '\'' +
                ", gender_id='" + gender_id + '\'' +
                ", lnick='" + lnick + '\'' +
                ", token='" + token + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
