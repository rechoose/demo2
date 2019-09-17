package cn.gw.demo2.pojo;

import io.swagger.annotations.ApiModelProperty;

public class SqlSorter {
    @ApiModelProperty("排序字段")
    private String sortField;
    @ApiModelProperty("顺序")
    private String order;

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }
}
