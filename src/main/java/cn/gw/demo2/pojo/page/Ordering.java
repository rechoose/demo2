package cn.gw.demo2.pojo.page;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;

@ApiModel
public class Ordering {
    @ApiModelProperty("排序字段")
    private String field;
    @ApiModelProperty("排序类型 正序：asc 倒序：desc")
    @Pattern(
            regexp = "(asc|desc)",
            message = "数据格式不正确，请传入asc或desc"
    )
    private String direction;

    public Ordering() {
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String toString() {
        return "[field=" + this.field + ", direction=" + this.direction + "]";
    }
}

