package cn.gw.demo2.factory;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapServiceConfig {

    @Bean
    public ServiceLocatorFactoryBean serviceFactoryBean(){
        ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
        bean.setServiceLocatorInterface(MapServiceFactory.class);
        return bean;
    }
}
