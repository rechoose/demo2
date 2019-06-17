package cn.gw.demo2.redissub;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
public class Redis2 extends RedisListener {

    private String keySlot = "";

    @Autowired
    private RedisClusterMessageSubscriber suber;

    @Override
    public void onMessage(String message) {
        System.out.println("消息1：" + message);
    }

//    @PostConstruct
    public void kk() {
        suber.subscribe(this);
    }
}
