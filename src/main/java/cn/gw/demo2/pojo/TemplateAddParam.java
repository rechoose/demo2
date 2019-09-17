package cn.gw.demo2.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TemplateAddParam extends TemplateParam{

    @ApiModelProperty("列名")
    private List<String> columns;
    @ApiModelProperty("新增数据")
    private List<Object> datas;

    public List<String> getColumns() {
        return columns;
    }
    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }
}
