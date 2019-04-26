package cn.gw.demo2.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 读取配置文件,获取数据源消息
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "mysql1")
    @ConfigurationProperties(prefix = "spring.datasource.mysql1")
    public DataSource mysql1() {
        return DataSourceBuilder.create().build();
    }
}
