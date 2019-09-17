package cn.gw.demo2.postconstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 程序初始化时,需要加载的内容,统一管理
 */
@Component
public class PostConstructOrder {

    private Logger log = LoggerFactory.getLogger(PostConstructOrder.class);

    @PostConstruct
    public void init() {
        log.info("start init some data...");
    }
}
