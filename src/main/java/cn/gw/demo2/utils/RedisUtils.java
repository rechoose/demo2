package cn.gw.demo2.utils;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCommands;

import java.util.concurrent.TimeUnit;

public class RedisUtils {

    //获取锁
    public static boolean lock(RedisTemplate redisTemplate, String lockKey, Long millisecond) {
        if (millisecond == null || millisecond < 1) {
            throw new IllegalArgumentException("may be a deadlock if expire second less than 1");
        }
        String execute = (String) redisTemplate.execute((RedisCallback<String>) connection -> {
            JedisCommands commands = (JedisCommands) connection.getNativeConnection();
            //参数3说明,nx:不存在,进行操作; xx:值存在,进行操作
            //参数4说明,px:时间按毫秒; ex:时间按秒
            return commands.set(lockKey, "iamlock", "NX", "PX", millisecond);
        });
        return !StringUtils.isEmpty(execute);
    }

    public static void unlock(RedisTemplate redisTemplate, String lockKey) {
        redisTemplate.delete(lockKey);
    }


    //非原子操作,ops.setIfAbsent()方法执行后宕机,会造成死锁(不推荐)
    public boolean getLock(RedisTemplate redisTemplate, String lockKey, Long seconds) {//获取锁
        ValueOperations ops = redisTemplate.opsForValue();
        Boolean aBoolean = ops.setIfAbsent(lockKey, "lock");//该方法会在没有key时，设置key,返回true;存在key时返回false
        if (aBoolean != null && aBoolean) {
            redisTemplate.expire(lockKey, seconds, TimeUnit.SECONDS);//设置锁的有效期,避免死锁
            return true;
        } else {
            return false;
        }
    }

    //非原子操作,会造成死锁(不推荐)
    public boolean lock1(RedisTemplate redisTemplate, String lockKey, Long seconds) {
        if (seconds == null || seconds < 1) {
            throw new IllegalArgumentException("may be a deadlock if expire second less than 1");
        }
        return (Boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Boolean aBoolean = connection.setNX(lockKey.getBytes(), "iamlock".getBytes());
            if (aBoolean != null && aBoolean) {
                connection.expire(lockKey.getBytes(), seconds);
                return true;
            } else {
                return false;
            }
        });
    }
}
