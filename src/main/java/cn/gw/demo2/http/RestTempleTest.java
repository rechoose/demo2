package cn.gw.demo2.http;

import cn.gw.demo2.utils.SerializeUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用restTemple进行restfull请求
 */
public class RestTempleTest {

    private String name;
    private int age;


    public static void getTest() {

        String url = "http://172.16.14.181:20014/api/jp-BIRM-UserProfile-ms/policestations/getChildStationBaseInfo?stationId=9BECB65D-4E52-4039-993B-369DAA145EAF&type=FJ";

        // 1.设置底层连接
        //生成一个设置了连接超时时间、请求超时时间、异常最大重试次数的httpClient
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000).setSocketTimeout(30000).build();
        HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(config).setRetryHandler(new DefaultHttpRequestRetryHandler(5, false));
        HttpClient httpClient = builder.build();
        //使用httpClient创建一个ClientHttpRequestFactory的实现
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        //ClientHttpRequestFactory作为参数构造一个使用作为底层的RestTemplate
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        // 2.设置转换器
      /*  //获取RestTemplate默认配置好的所有转换器
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        //默认的MappingJackson2HttpMessageConverter在第7个 先把它移除掉
        messageConverters.remove(6);
        //添加上GSON的转换器, 或者自己重写转换方法
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        messageConverters.add(6, converter);
//        messageConverters.add(6, new MyConver());*/

        // 3.拦截器
//        restTemplate.getInterceptors().add(new MyIntercept());
        Object requestBody = new Object();

        //post请求
//        RestTempleTest responce = restTemplate.postForObject("url", requestBody, RestTempleTest.class);
        //get请求
        String resp = restTemplate.getForObject(url, String.class);
        try {
            List<Object> objects = SerializeUtil.toObject(resp, new TypeReference<List<Object>>() {
            });
            for (Object object : objects) {
                System.out.println(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("");


    }

    public static void main(String[] args) {
        getTest();
    }


    //自定义转换对象
    class MyConver implements HttpMessageConverter<RestTempleTest> {


        @Override
        public boolean canRead(Class<?> aClass, MediaType mediaType) {
            return false;
        }

        @Override
        public boolean canWrite(Class<?> aClass, MediaType mediaType) {
            return false;
        }

        @Override
        public List<MediaType> getSupportedMediaTypes() {
            return null;
        }

        @Override
        public RestTempleTest read(Class<? extends RestTempleTest> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
            return null;
        }

        @Override
        public void write(RestTempleTest restTempleTest, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

        }
    }

    //自定义拦截器
    class MyIntercept implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
            return null;
        }
    }

}
