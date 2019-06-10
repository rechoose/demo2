package cn.gw.demo2.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AsyncExecutor {

    @Async("asyncExecutor")
    public void ddd() {
        try {
            String name = Thread.currentThread().getName();
            log.debug("异步线程:{}执行开始...", name);
            Thread.sleep(5000);
            log.debug("异步线程:{}执行结束...", name);
        } catch (InterruptedException e) {
            log.error("异常:{}", e.getMessage());
        }
    }

}
