package cn.gw.demo2.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class GlobeExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponceResult nullPointerExceptionHandler(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        return ResponceResult.fale(ex.getMessage());
    }
}
