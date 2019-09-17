package cn.gw.demo2.controller;

import cn.gw.demo2.io.MessageIO;
import cn.gw.demo2.pojo.StudentDto;
import cn.gw.demo2.pojo.page.PageReq;
import cn.gw.demo2.service.StudentService;
import cn.gw.demo2.utils.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.UUID;


@RestController
@RequestMapping("/carcontrol")
@Api("车辆管控")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private MessageIO messageIO;

    @GetMapping("/getcardaystream/{id}")
    @ApiOperation("获取每日车流量信息")
    public StudentDto getCarDayStream(@PathVariable("id") String id) {
        try {
            return studentService.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/send")
    @ApiOperation("获取每日车流量信息")
    public void sendMsg() {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            StudentDto studentDto = new StudentDto();
            studentDto.setId(UUID.randomUUID().toString());
            if (i % 2 == 0) {
                studentDto.setName(CommonUtils.getStringRandom(3 + new Random().nextInt(7)));
            } else {
                studentDto.setName(CommonUtils.randomGBKJianHan(2 + new Random().nextInt(2)));
            }
         /*   String names = "编码字符串字节数组解码：字节数组->字符串。出现乱码就是编码使用的格式与解码使用的格式不一样导致码表中无法读取正确的字符（我们会使用的基本也就是GBK和UTF-8，乱码也就是没有控制两者的统一）";
            int i1 = random.nextInt(names.length() - 4);
            studentDto.setName(names.substring(i1, i1 + random.nextInt(4)));*/
            studentDto.setSex(new Random().nextInt(80));
            Message<StudentDto> ss = MessageBuilder.withPayload(studentDto).build();
            messageIO.output().send(ss);
        }
    }

    @PostMapping("/list")
    @ApiOperation("获取列表,排序,分页")
    public Page list(@RequestParam(required = false) String keyWord, @RequestBody PageReq pageRequest) {
        try {
            return studentService.list(keyWord, pageRequest);
        } catch (Exception e) {

            return null;

        }
    }
}
