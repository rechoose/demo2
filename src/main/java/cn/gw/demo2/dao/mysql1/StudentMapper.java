package cn.gw.demo2.dao.mysql1;

import cn.gw.demo2.pojo.StudentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StudentMapper {

    StudentDto findOne(@Param("id") String id);

    void insert(@Param("studentDto") StudentDto studentDto) throws Exception;

    List<StudentDto> listByKeyWord(Map<String, Object> param) throws Exception;

    List<StudentDto> listByIds(Map<String, Object> param) throws Exception;

    List<StudentDto> findByIds(@Param("ids") List<String> ids);
}
