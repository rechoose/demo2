package cn.gw.demo2.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SqlCondition implements Serializable {

    @ApiModelProperty("过滤条件key")
    private String field;
    @ApiModelProperty("过滤条件值")
    private Object value;
    @ApiModelProperty("过滤条件符号")
    private String symbol;

    public SqlCondition(){};

    public SqlCondition(String field, Object value) {
        this.field = field;
        this.symbol = "=";
        this.value = value;
    }

    public SqlCondition(String field,String symbol, Object value) {
        this.field = field;
        this.symbol = symbol;
        this.value = value;
    }


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
}
