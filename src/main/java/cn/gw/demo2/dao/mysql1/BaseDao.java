package cn.gw.demo2.dao.mysql1;

import cn.gw.demo2.pojo.*;

import java.util.List;
import java.util.Map;

/**
 * 父类dao 通用dao
 */
public interface BaseDao {

    Integer addData(TemplateAddParam addParam) throws Exception;

    Integer addBatchData(TemplateAddBatchParam addBatchParam) throws Exception;

    Integer deleteData(TemplateDeleteParam deleteParam) throws Exception;

    Integer updateData(TemplateUpdateParam updateParam) throws Exception;

    List<Map<String, Object>> queryPageData(TemplateQueryParam templateQueryParam);

    List<Map<String, Object>> queryPageDataCompatibility(TemplateQueryParam templateQueryParam);

    Integer count(TemplateQueryParam templateQueryParam);

    Integer countCompatibility(TemplateQueryParam templateQueryParam);

}
