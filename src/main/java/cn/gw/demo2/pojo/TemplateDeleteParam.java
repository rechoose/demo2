package cn.gw.demo2.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TemplateDeleteParam  extends TemplateParam{

    //必须含主键
    @ApiModelProperty("删除条件")
    private List<SqlCondition> conditions;

    public List<SqlCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<SqlCondition> conditions) {
        this.conditions = conditions;
    }
}
