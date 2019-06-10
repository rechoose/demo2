package cn.gw.demo2.lock;

public interface LockService {

    boolean lock(String lockKey, Long second);

    void unlock(String lockKey);
}
