package com.BaseEntity;

import java.io.Serializable;

public class Page implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer totalCount;
    private Integer pageCount;
    private String sort;
    private String order;
    private Boolean firstPage;
    private Boolean hasPrev;
    private Boolean hasNext;
    private Boolean lastPage;
    private Boolean selectpagecount;
    private Object data;
    private int code;

    public Page(Integer pageIndex) {
        this(pageIndex, DEFAULT_PAGE_SIZE);
    }

    public Page() {
        this.pageIndex = 0;
        this.pageSize = 10;
        this.totalCount = 0;
        this.pageCount = 1;
        this.firstPage = false;
        this.hasPrev = false;
        this.hasNext = false;
        this.lastPage = false;
        this.selectpagecount = true;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Page(Integer pageIndex, Integer pageSize) {
        this.pageIndex = 0;
        this.pageSize = 10;
        this.totalCount = 0;
        this.pageCount = 1;
        this.firstPage = false;
        this.hasPrev = false;
        this.hasNext = false;
        this.lastPage = false;
        this.selectpagecount = true;
        if (pageIndex < 1) {
            pageIndex = 1;
        }

        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public Page(org.springframework.data.domain.Page page) {
        this.pageIndex = 0;
        this.pageSize = 10;
        this.totalCount = 0;
        this.pageCount = 1;
        this.firstPage = false;
        this.hasPrev = false;
        this.hasNext = false;
        this.lastPage = false;
        this.selectpagecount = true;
        this.pageIndex = page.getNumber();
        this.pageSize = page.getSize();
        this.data = page.getContent();
        this.totalCount = Math.toIntExact(page.getTotalElements());
        this.code = 0;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Integer getPageIndex() {
        if (this.pageIndex < 1) {
            this.pageIndex = 1;
        }

        if (this.pageSize < 1) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }

        return this.pageIndex;
    }

    public Integer getPageSize() {
        if (this.pageSize < 1) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }

        return this.pageSize;
    }

    public Integer getPageCount() {
        return this.pageCount;
    }

    public Integer getFirstResult() {
        return (this.pageIndex - 1) * this.pageSize;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        this.pageCount = (totalCount + this.pageSize - 1) / this.pageSize;
        if (this.pageIndex >= this.pageCount) {
            this.lastPage = true;
        } else {
            this.hasNext = true;
        }

        if (this.getPageIndex() > 1) {
            this.hasPrev = true;
        } else {
            this.firstPage = true;
        }

    }

    public Boolean isEmpty() {
        return this.totalCount < 1;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Boolean getFirstPage() {
        return this.firstPage;
    }

    public Boolean getHasPrev() {
        return this.hasPrev;
    }

    public Boolean getLastPage() {
        return this.lastPage;
    }

    public Boolean getHasNext() {
        return this.hasNext;
    }

    public Boolean getSelectpagecount() {
        return this.selectpagecount;
    }

    public void setSelectpagecount(boolean selectpagecount) {
        this.selectpagecount = selectpagecount;
    }

    public Boolean isFirstPage() {
        return this.firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public Boolean isHasNext() {
        return this.hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Boolean isLastPage() {
        return this.lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public String toString() {
        return "pageIndex[" + this.getPageIndex() + "]," + "sort[" + this.getSort() + "]," + "order[" + this.getOrder() + "]";
    }
}