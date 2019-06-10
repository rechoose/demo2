package cn.gw.demo2.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/excel")
@Api("excel输出")
@Slf4j
public class ExcelOutController {

    public void exccel(HttpServletResponse response) {
        OutputStream out = null;
        try {
            out = response.getOutputStream();


        } catch (IOException e) {
            log.error("异常:{}", e.getMessage());
        }
    }
}
