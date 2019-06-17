package cn.gw.demo2.redissub;

//redis消息订阅
public interface RedisMessageSubscriber {

    void subscribe(RedisListener listener);
}
