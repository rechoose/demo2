package cn.gw.demo2.pojo.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;

/**
 * @author alzer
 * @description 分页返回的结果对象
 */
@ApiModel("分页响应统一模型")
public class PageResp<T> implements Page<T> {
    @ApiModelProperty("分页数据")
    private List<T> pageData;
    @ApiModelProperty("查出的总记录数")
    private long elementsSum;
    @JsonIgnore
    private Page<T> page;

    public PageResp(Page<T> pageData) {
        this.page = pageData;
        this.pageData = pageData.getContent();
        this.elementsSum = pageData.getTotalElements();
    }

    public PageResp(List<T> pageData, long elementSum) {
        this.pageData = pageData;
        this.elementsSum = elementSum;
    }

    public PageResp() {
        super();
    }

    public List<T> getData() {
        return pageData;
    }

    public void setData(List<T> pageData) {
        this.pageData = pageData;
    }

    public long getElementsSum() {
        return elementsSum;
    }

    public void setElementsSum(long elementsSum) {
        this.elementsSum = elementsSum;
    }

    @Override
    @JsonIgnore
    public int getNumber() {
        return page.getNumber();
    }

    @Override
    @JsonIgnore
    public int getSize() {
        return page.getSize();
    }

    @Override
    @JsonIgnore
    public int getNumberOfElements() {
        return page.getNumberOfElements();
    }

    @Override
    @JsonIgnore
    public List<T> getContent() {
        return pageData;
    }

    @Override
    @JsonIgnore
    public boolean hasContent() {
        return pageData != null && (!pageData.isEmpty());
    }

    @Override
    @JsonIgnore
    public Sort getSort() {
        return page.getSort();
    }

    @Override
    @JsonIgnore
    public boolean isFirst() {
        return page.isFirst();
    }

    @Override
    @JsonIgnore
    public boolean isLast() {
        return page.isLast();
    }

    @Override
    @JsonIgnore
    public boolean hasNext() {
        return page.hasNext();
    }

    @Override
    @JsonIgnore
    public boolean hasPrevious() {
        return page.hasPrevious();
    }

    @Override
    @JsonIgnore
    public Pageable nextPageable() {
        return page.nextPageable();
    }

    @Override
    @JsonIgnore
    public Pageable previousPageable() {
        return page.previousPageable();
    }

    @Override
    @JsonIgnore
    public Iterator<T> iterator() {
        return page.iterator();
    }

    @Override
    @JsonIgnore
    public int getTotalPages() {
        return page.getTotalPages();
    }

    @Override
    @JsonIgnore
    public long getTotalElements() {
        return page.getTotalElements();
    }

    @Override
    @JsonIgnore
    public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
        return page.map(converter);
    }

    @Override
    public String toString() {
        return "[pageData=" + pageData + ", elementsSum=" + elementsSum + "]";
    }
}