package com.chloneda.beans;

import java.io.Serializable;

/**
 * 分页通用请求体，通过 <继承> 或 <泛型> 确定分页参数和查询条件
 *
 * @author chloneda
 */
public class PageRequest<IN> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_PAGE_INDEX = 1;

    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 当前页码，默认：1
     */
    private int pageIndex = DEFAULT_PAGE_INDEX;

    /**
     * 每页大小，默认：20
     */
    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 查询条件
     */
    private IN param;

    public IN getParam() {
        return param;
    }

    public void setParam(IN param) {
        this.param = param;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, DEFAULT_PAGE_INDEX);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

}
