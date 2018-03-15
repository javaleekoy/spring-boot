package com.peramdy.es.spring;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author pd 2018/3/15.
 */
public class EsPage<T> {

    private String scroll;
    /**
     * 总条数
     */
    private long total;
    /**
     * 每页显示大小
     */
    private int pageSize;
    /**
     * 当前页
     */
    private int pageNo;
    /**
     * 返回实体类
     */
    private T param;
    /**
     * 返回实体类集合
     */
    private List<T> retList;

    public String getScroll() {
        return scroll;
    }

    public void setScroll(String scroll) {
        this.scroll = scroll;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    public List<T> getRetList() {
        return retList;
    }

    public void setRetList(List<T> retList) {
        this.retList = retList;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
