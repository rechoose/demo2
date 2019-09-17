package cn.gw.demo2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo2ApplicationTests {

    @Test
    public void contextLoads() {
    }


    @SuppressWarnings("unchecked")
    @Test
    public void testSpring() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring/spring.xml");

        Map<String, String> systemPropertiesBean = (Map<String, String>) ac.getBean("systemProperties");
        for (Map.Entry<String, String> entry : systemPropertiesBean.entrySet()) {
            System.out.println(entry.getKey() + "--->" + entry.getValue());
        }

        System.out.println("==============================华丽的分隔符==============================");
        Map<String, String> systemEnvironmentBean = (Map<String, String>) ac.getBean("systemEnvironment");
        for (Map.Entry<String, String> entry : systemEnvironmentBean.entrySet()) {
            System.out.println(entry.getKey() + "--->" + entry.getValue());
        }
    }


}
