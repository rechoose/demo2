package cn.gw.demo2.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TemplateAddBatchParam extends TemplateParam{

    @ApiModelProperty("列名")
    private List<String> columns;
    @ApiModelProperty("新增数据")
    private List<List<Object>> datasList;

    public List<String> getColumns() {
        return columns;
    }
    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<List<Object>> getDatasList() {
        return datasList;
    }

    public void setDatasList(List<List<Object>> datasList) {
        this.datasList = datasList;
    }
}
