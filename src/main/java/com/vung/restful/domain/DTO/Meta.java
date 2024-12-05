package com.vung.restful.domain.DTO;

public class Meta {
    private int page;
    private int pageSize;
    private int Pages;
    private int total;
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPages() {
        return Pages;
    }
    public void setPages(int pages) {
        Pages = pages;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    
}
