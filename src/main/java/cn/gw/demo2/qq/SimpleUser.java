package cn.gw.demo2.qq;

import lombok.Data;

@Data
public class SimpleUser {

    private String data;//QQ号

    private String label;//用户名/备注名

    private String nick;//昵称

    private String gender;//性别 1为男  2为女

    private String city;//城市

    private String country;//国家

    private String lnick;//个性签名

    private String personal;//个人说明

    private String college;//学校

    private String birthday;//生日

    private String email;//邮箱

    private String phone;//手机

    private String uin;//qq号

    private int age;

    @Override
    public String toString() {
        return "SimpleUser [data=" + data + ", label=" + label + ", nick=" + nick + ", gender=" + gender + ", city=" + city
                + ", country=" + country + ", lnick=" + lnick + ", personal=" + personal + ", college=" + college
                + ", birthday=" + birthday + ", email=" + email + ", phone=" + phone + "]";
    }


}
