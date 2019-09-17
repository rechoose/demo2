package cn.gw.demo2.service;

import cn.gw.demo2.pojo.StudentDto;
import cn.gw.demo2.pojo.queryMO.StudentQuery;
import cn.gw.demo2.service.impl.MongoInImpl;
import cn.gw.demo2.service.impl.MongoOutImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.List;

@Service
public class StudentDtoCacheIn {

    private Logger log = LoggerFactory.getLogger(StudentDtoCacheIn.class);
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private StudentService studentService;

    private String collectionName = "";
    private String bizKey = "";

    public void cacheIn() {
        new MongoInImpl<StudentQuery, StudentDto>(mongoTemplate, collectionName) {
            @Override
            public Collection<StudentDto> cacheAll(StudentQuery studentQuery) {
                List<StudentDto> byIds = null;
                try {
                    byIds = studentService.findByIds(studentQuery.getIds());
                } catch (Exception e) {
                    log.error("异常:{}", e.getMessage());
                }
                Query query = new Query(Criteria.where("bizKey").is(bizKey));
                this.removeAndinsert(byIds, query, StudentDto.class);
                return byIds;
            }
        };

    }

    public void cacheOut() {
        MongoOutImpl<StudentQuery, StudentDto> mongoOut = new MongoOutImpl<StudentQuery, StudentDto>(mongoTemplate, collectionName) {
            @Override
            public Collection<StudentDto> list(StudentQuery studentQuery) {
                Collection<StudentDto> list = this.list(new Query(), StudentDto.class);
                return list;
            }
        };
        Collection<StudentDto> result = mongoOut.getResult();
    }

    public static void main(String[] args) {
        String filePath = "f:"+File.separator+"test1"+File.separator+"test1.txt";
        File file = new File(filePath);
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
        }
    }
}
