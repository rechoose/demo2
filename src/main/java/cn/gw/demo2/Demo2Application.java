package cn.gw.demo2;

import cn.gw.demo2.utils.PropertiesLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class Demo2Application {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Demo2Application.class);
        springApplication.addListeners(new PropertiesLogger());
        springApplication.run(args);
    }

}
