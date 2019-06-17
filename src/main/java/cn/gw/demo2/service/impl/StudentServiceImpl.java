package cn.gw.demo2.service.impl;

import cn.gw.demo2.dao.mysql1.StudentMapper;
import cn.gw.demo2.pojo.StudentDto;
import cn.gw.demo2.pojo.page.Ordering;
import cn.gw.demo2.pojo.page.PageReq;
import cn.gw.demo2.pojo.page.PageResp;
import cn.gw.demo2.service.StudentService;
import cn.gw.demo2.utils.CommonUtils;
import cn.gw.demo2.utils.SerializeUtil;
import cn.gw.demo2.utils.XLSXCovertCSVReader;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MongoTemplate mongoTemplate;

    private Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public StudentDto findOne(String id) throws Exception {
        StudentDto one = studentMapper.findOne(id);
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("test", SerializeUtil.toJson(one), 10000, TimeUnit.MILLISECONDS);
        return one;
    }

    @Override
    public List<StudentDto> findByIds(List<String> ids) throws Exception {
        List<StudentDto> result = new ArrayList<>();
        List<List<String>> lists = CommonUtils.split1000(ids);
        for (List<String> list : lists) {
            List<StudentDto> temp = studentMapper.findByIds(list);
            result.addAll(temp);
        }
        return result;
    }

    @Override
    public void insert(StudentDto studentDto) throws Exception {
        studentMapper.insert(studentDto);
        mongoTemplate.insert(studentDto, "myfirst_coll");
    }

    @Override
    public Page<StudentDto> list(String keyWord, PageReq pageRequest) throws Exception {
        Map<String, Object> param = new HashMap<>();
        if (!StringUtils.isEmpty(keyWord)) {
            keyWord = "%" + keyWord + "%";
        }
        param.put("name", keyWord);
        List<Ordering> sort = pageRequest.getSort();
        StringBuffer stringBuffer = new StringBuffer();
        if (!CollectionUtils.isEmpty(sort)) {
            for (Ordering ordering : sort) {
                String field = ordering.getField();
                String direction = ordering.getDirection();
                stringBuffer.append(field).append(" ").append(direction).append(",");
            }
            int i = stringBuffer.lastIndexOf(",");
            stringBuffer.deleteCharAt(i);
        }
        PageHelper.startPage(pageRequest.getPageIndex(), pageRequest.getPageSize(), stringBuffer.toString());
        List<StudentDto> studentDtos = studentMapper.listByKeyWord(param);
        PageInfo<StudentDto> info = new PageInfo<>(studentDtos);
        XLSXCovertCSVReader.writeTo2003Excel(info.getList(), StudentDto.class, "E:\\test\\demo2\\src\\main\\resources\\excel");
        XLSXCovertCSVReader.writeTo2007Excel(info.getList(), StudentDto.class, "E:\\test\\demo2\\src\\main\\resources\\excel");
        PageResp<StudentDto> resp = new PageResp<>();
        resp.setData(info.getList());
        resp.setElementsSum(info.getTotal());
        return resp;
    }

}
