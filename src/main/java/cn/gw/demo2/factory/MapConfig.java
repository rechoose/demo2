package cn.gw.demo2.factory;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "map-service")
public class MapConfig {

    private String name = "a";
}
