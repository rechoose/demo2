package cn.gw.demo2.qq;

import lombok.Data;

@Data
public class Consumer {
    private String name;
    private String num;
    private String time;//时段
    private int cost;
    private int surplus;
    private String nextName;
}
