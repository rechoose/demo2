package cn.gw.demo2.schedules;

import cn.gw.demo2.constant.LockKey;
import cn.gw.demo2.lock.SingleTaskLock;
import cn.gw.demo2.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SyncData {

    @Autowired
    private RedisTemplate redisTemplate;

//    @Scheduled(cron = "0 */1 * * * ?")
    public void init() throws Exception {
        if (RedisUtils.lock(redisTemplate, LockKey.LOCK_INIT, 110*1000L)) {//拿到锁,执行完后要释放锁
            System.out.println("拿到锁,执行...");

            Thread.sleep(5000);

            RedisUtils.unlock(redisTemplate, LockKey.LOCK_INIT);//释放锁
            System.out.println("任务结束,释放锁...");
        } else {//拿不到锁,???
            System.out.println("拿不到锁,执行等待...");
        }
    }

    @SingleTaskLock(expire = 20)
    @Scheduled(cron = "0 */1 * * * ?")
    public void ano(){
        System.out.println("拿到锁,执行...");
    }



}
