package cn.gw.demo2.service;

import cn.gw.demo2.pojo.StudentDto;
import cn.gw.demo2.pojo.page.PageReq;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

    StudentDto findOne(String id) throws Exception;

    List<StudentDto> findByIds(List<String> ids) throws Exception;

    void insert(StudentDto studentDto) throws Exception;

    Page<StudentDto> list(String keyWord, PageReq pageRequest) throws Exception;
}
