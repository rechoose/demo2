package cn.gw.demo2.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TemplateQueryParam extends TemplateParam{

    @ApiModelProperty("列名")
    private List<String> columns;
    @ApiModelProperty("查询条件")
    private List<SqlCondition> conditions;
    @ApiModelProperty("排序")
    private List<SqlSorter> sorters;
    public List<SqlCondition> getConditions() {
        return conditions;
    }
    public void setConditions(List<SqlCondition> conditions) {
        this.conditions = conditions;
    }

    public List<SqlSorter> getSorters() {
        return sorters;
    }

    public void setSorters(List<SqlSorter> sorters) {
        this.sorters = sorters;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    @ApiModelProperty("当前页码 默认值为1")
    private Integer pageIndex;
    @ApiModelProperty("每页显示的条数 默认值10")
    private Integer pageSize;

    public Integer getPageIndex() {
        if (this.pageIndex == null || this.pageIndex <= 0) {
            return 1;
        }
        return pageIndex;
    }
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }


    public Integer getPageSize() {
        if (this.pageSize == null) {
            return 10;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
