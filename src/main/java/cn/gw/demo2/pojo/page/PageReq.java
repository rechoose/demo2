package cn.gw.demo2.pojo.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ApiModel("分页对象请求模型")
public class PageReq implements Serializable {
    private static final long serialVersionUID = -8859054476799842586L;
    @ApiModelProperty("当前页,begin with 1.")
    @Max(9999999L)
    private int pageIndex = 1;
    @ApiModelProperty("每页条数,默认10条")
    @Range(
            min = 1L,
            max = 999999L
    )
    private int pageSize = 10;
    @ApiModelProperty("排序集合")
    @Valid
    private List<Ordering> sort = new ArrayList();

    public PageReq() {
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<Ordering> getSort() {
        return this.sort;
    }

    @ModelAttribute
    public void setSort(List<Ordering> sort) {
        this.sort = sort;
    }

    public Sort getSpringBootDataJpaSort() {
        List<Sort.Order> orders = new ArrayList();
        Iterator var2 = this.sort.iterator();

        while(var2.hasNext()) {
            Ordering ordering = (Ordering)var2.next();
            if (StringUtils.isNotEmpty(ordering.getDirection()) && StringUtils.isNotEmpty(ordering.getField())) {
                orders.add(new Sort.Order(Sort.Direction.fromString(ordering.getDirection()), ordering.getField()));
            }
        }

        return new Sort(orders);
    }

    public String toString() {
        return "[pageIndex=" + this.pageIndex + ", pageSize=" + this.pageSize + ", sort=" + this.sort + "]";
    }
}
