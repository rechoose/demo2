package cn.gw.demo2.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"cn.gw.demo2.dao.mysql1"}, sqlSessionFactoryRef = "sqlSessionFactory1")
public class Mysql1DataSource {

    @Autowired
    @Qualifier("mysql1")//合格者 , 接口如果有多个实现类,以此注解标示
    private DataSource mysql1;

    // ====================================数据源配置jpa====================================//
   /* @Autowired
    private JpaProperties jpaProperties;

    @Primary
    @Bean(name = "entityManager")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dashboard).properties(getVendorProperties(dashboard))
                .packages("com.jp.hczz.situationdashboard.app.entity.dashboard") // 设置实体类所在位置
                .persistenceUnit("primaryPersistenceUnit").build();
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }*/
    // ====================================数据源配置jpa====================================//




    // ==================================数据源配置mybatis==================================//
    @Bean
    public SqlSessionFactory sqlSessionFactory1() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(mysql1); //
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mysql1/*.xml"));
        return factoryBean.getObject();

    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate1() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory1()); // 使用上面配置的Factory
    }
    // ==================================数据源配置mybatis==================================//
}
