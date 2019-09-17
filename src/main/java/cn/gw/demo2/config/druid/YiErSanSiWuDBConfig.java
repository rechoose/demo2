//package cn.gw.demo2.config.druid;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//
//@Configuration
//@MapperScan(basePackages = {"com.jp.bspa.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
//public class YiErSanSiWuDBConfig {
//
//    @Autowired
//    @Qualifier("YiErSanSiWu")
//    private DruidDataSource YiErSanSiWuDataSource;
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(YiErSanSiWuDataSource);
//        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
//        return factoryBean.getObject();
//
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate2() throws Exception {
//        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory());
//        return template;
//    }
//
//
//}
