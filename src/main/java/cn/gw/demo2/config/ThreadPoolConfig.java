package cn.gw.demo2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步线程配置
 */
@Data
@Configuration
@ConfigurationProperties("thread.async")
public class ThreadPoolConfig {
    private Integer corePoolSize = 30;//线程池维护线程的最少数量

    private Integer maxPoolSize = 50;//线程池维护线程的最大数量

    private Integer keepAliveSeconds = 60;//允许的空闲时间/秒

    private Integer queueCapacity = 5;//缓存队列

    @Bean(name = "asyncExecutor")
    public TaskExecutor getExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Async-");
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setQueueCapacity(queueCapacity);
        executor.afterPropertiesSet();
        return executor;
    }

}
