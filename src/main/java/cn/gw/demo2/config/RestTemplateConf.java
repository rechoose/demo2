package cn.gw.demo2.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * RestTemplateConf
 *
 * @Author: kevin yang
 * @Description:
 * @Date: Created at 19-2-19
 * @Last Modified:
 **/

@Configuration
@Slf4j
public class RestTemplateConf {

    @Bean
    public RestTemplate restTemplate() {
        // 处理gzip压缩问题，
        // 参考：https://stackoverflow.com/questions/34415144/how-to-parse-gzip-encoded-response-with-resttemplate-from-spring-web
        // 使用HttpClient替换默认实现
        HttpClient httpClient = HttpClientBuilder.create().build();
        ClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        // restTemplate默认会向消息头中写入大量Accept-Charset信息，在调用部分接口时（例如：天翼点位），会出现因无法处理这些头信息而卡死的情况
        // 因此需要将默认写入的accept-charset头禁用掉
        converter.setWriteAcceptCharset(false);
        // 避免中文乱码
        restTemplate.getMessageConverters().set(1, converter);
        return restTemplate;
    }

}
