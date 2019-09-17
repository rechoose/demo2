package cn.gw.demo2.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

public class TemplateUpdateParam extends TemplateParam{

    @ApiModelProperty("要修改的键值对")
    private Map<String,Object> data;
    //必须含主键
    @ApiModelProperty("修改条件")
    private List<SqlCondition> conditions;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public List<SqlCondition> getConditions() {
        return conditions;
    }
    public void setConditions(List<SqlCondition> conditions) {
        this.conditions = conditions;
    }

}
