package cn.gw.demo2.pojo;

import io.swagger.annotations.ApiModelProperty;

public abstract class TemplateParam {
    @ApiModelProperty("数据源")
    private String dataTypeCode;
    @ApiModelProperty("表名")
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataTypeCode() {
        return dataTypeCode;
    }

    public void setDataTypeCode(String dataTypeCode) {
        this.dataTypeCode = dataTypeCode;
    }
}
