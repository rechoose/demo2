//package cn.gw.demo2.config.druid;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManager;
//import java.util.Map;
//
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef="entityManagerFactory",
//        transactionManagerRef="transactionManager",
//        basePackages= { "com.jp.bspa.entity" }) //设置Repository所在位置
//public class BioDBConfig {
//
//    @Autowired
//    @Qualifier("bio")
//    private DruidDataSource bioDataSource;
//
//    @Autowired
//    private JpaProperties jpaProperties;
//
//
//    @Primary
//    @Bean(name = "entityManager")
//    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
//        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
//    }
//
//    @Primary
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary (EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(bioDataSource)
//                .properties(getVendorProperties(bioDataSource))
//                .packages("com.jp.bspa.entity") //设置实体类所在位置
//                .persistenceUnit("primaryPersistenceUnit")
//                .build();
//    }
//
//    private Map<String, String> getVendorProperties(DruidDataSource dataSource) {
//        return jpaProperties.getHibernateProperties(dataSource);
//    }
//
//    @Primary
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
//        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
//    }
//
//
//
//}
