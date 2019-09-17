package cn.gw.demo2.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class DataSourceConfig {

//    @Bean(name = "bio")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.bio") // bootstrap.yml中对应属性的前缀
//    public DruidDataSource dataSource1() {
//        return DruidDataSourceBuilder.create().build();
//    }
//
//
//    @Bean(name = "YiErSanSiWu")
//    @ConfigurationProperties(prefix = "spring.YiErSanSiWu") // bootstrap.yml中对应属性的前缀
//    public DruidDataSource dataSource2() {
//        return DruidDataSourceBuilder.create().build();
//    }

    @Bean(name = "mysql1")
    @ConfigurationProperties(prefix = "spring.datasource.mysql1") // bootstrap.yml中对应属性的前缀
    public DruidDataSource mysql1() {
        return DruidDataSourceBuilder.create().build();
    }

}
