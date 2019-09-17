package cn.gw.demo2.reflect1;

import java.lang.reflect.Type;

public class ObjectTest extends FObject {

    private final String ss = "123";

    private String id;
    private String name;
    private String sex;
    Number cord;

    public String getId() {
        return id;
    }

    private void tt() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Number getCord() {
        return cord;
    }

    public void setCord(Number cord) {
        this.cord = cord;
    }
}
