package cn.gw.demo2.dao.mysql1;


import cn.gw.demo2.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PointResourceDao extends BaseDao {


    Integer addData(TemplateAddParam addParam) throws Exception;

    Integer addBatchData(TemplateAddBatchParam addBatchParam) throws Exception;

    Integer deleteData(TemplateDeleteParam deleteParam) throws Exception;

    Integer updateData(TemplateUpdateParam updateParam) throws Exception;

    Integer count(TemplateQueryParam templateQueryParam);

}
