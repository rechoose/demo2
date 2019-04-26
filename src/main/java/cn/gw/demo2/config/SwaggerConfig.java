package cn.gw.demo2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;

@Configuration
@EnableSwagger2
@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true, encoding = "UTF-8")//防止编码问题
@ConfigurationProperties(prefix = "spring.swagger")
@Data
public class SwaggerConfig {
    private String basePackage;
    private Map<String, String> apiInfo;

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    //此部分可以提出来,作为配置项进行配置
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title(this.apiInfo.get("title"))
                //创建人
                .contact(new Contact(this.apiInfo.get("contact"), this.apiInfo.get("url"), this.apiInfo.get("email")))
                //版本号
                .version(this.apiInfo.get("version"))
                //描述
                .description(this.apiInfo.get("description"))
                .build();
    }

}