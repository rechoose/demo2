package cn.gw.demo2.redissub;

import redis.clients.jedis.JedisPubSub;

/**
 * 实现该类,监听redis过期消息
 */
public abstract class RedisListener extends JedisPubSub {

    @Override
    public synchronized final void onPMessage(String pattern, String channel, String message) {
        onMessage(message);
    }

    public abstract String getKeySlot();//监听的key槽位

    public abstract void onMessage(String message);
}
