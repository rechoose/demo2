package cn.gw.demo2.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCommands;

@Service
public class LockServiceImpl implements LockService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean lock(String lockKey, Long second) {
        if (second == null || second < 1) {
            throw new IllegalArgumentException("may be a deadlock if expire second less than 1");
        }
        String execute = (String) redisTemplate.execute((RedisCallback<String>) connection -> {
            JedisCommands commands = (JedisCommands) connection.getNativeConnection();
            //参数3说明,nx:不存在,进行操作; xx:值存在,进行操作
            //参数4说明,px:时间按毫秒; ex:时间按秒
            return commands.set(lockKey, "iamlock", "NX", "EX", second);
        });
        return !StringUtils.isEmpty(execute);
    }

    @Override
    public void unlock(String lockKey) {
        redisTemplate.delete(lockKey);
    }
}
