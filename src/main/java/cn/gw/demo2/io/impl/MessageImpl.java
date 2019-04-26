package cn.gw.demo2.io.impl;

import cn.gw.demo2.io.MessageIO;
import cn.gw.demo2.pojo.StudentDto;
import cn.gw.demo2.service.StudentService;
import cn.gw.demo2.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(MessageIO.class)
public class MessageImpl {

    @Autowired
    private StudentService studentService;

    private Logger log = LoggerFactory.getLogger(MessageImpl.class);

    @StreamListener(MessageIO.EVENT_ONE_CONSUMER)
    public void getMsg(String msg) {
        try {
            log.info("接受rabbit消息:{}", msg);
            StudentDto studentDto = SerializeUtil.toObject(msg, StudentDto.class);
            studentService.insert(studentDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
