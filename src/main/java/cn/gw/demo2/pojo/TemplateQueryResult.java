package cn.gw.demo2.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

public class TemplateQueryResult {

    private List<Map<String,Object>> dataList;
    @ApiModelProperty("当前页码 默认值为1")
    private Integer pageIndex;
    @ApiModelProperty("每页显示的条数 默认值10")
    private Integer pageSize;
    @ApiModelProperty("总记录数")
    private Long total;

    public Integer getPageIndex() {
        return pageIndex;
    }
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }

    public List<Map<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }


}
