package com.chloneda.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 通用分页
 *
 * @author chloneda
 * @see Page
 * @see ResultBean
 * @see PageResultBean
 */
@Data
public class PageBean<T> extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码，从 1 开始
     */
    private int pageIndex;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int totalPage;

    public PageBean(List<T> result, long totalCount) {
        super(result, totalCount);
    }

    public PageBean(List<T> result, int pageIndex, int pageSize) {
        super(result, (Long) null);
        this.pageIndex = Math.max(pageIndex, DEFAULT_PAGE_INDEX);
        this.pageSize = pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public PageBean(List<T> result, int pageIndex, int pageSize, long totalCount) {
        super(result, totalCount);
        this.pageIndex = Math.max(pageIndex, DEFAULT_PAGE_INDEX);
        this.pageSize = pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
        this.totalPage = pageSize == 0 ? 0 : Math.toIntExact(totalCount % (long) pageSize == 0L ? totalCount / (long) pageSize : totalCount / (long) pageSize + 1L);
    }

    public static PageBean page(List result, long totalCount) {
        return new PageBean(result, totalCount);
    }

    public static PageBean page(List result, int totalCount) {
        return new PageBean(result, (long) totalCount);
    }

    public static PageBean page(List result, int pageIndex, int pageSize) {
        return new PageBean(result, pageIndex, pageSize);
    }

    public static PageBean page(List result, int pageIndex, int pageSize, long totalCount) {
        return new PageBean(result, pageIndex, pageSize, totalCount);
    }

    public static PageBean page(List result, int pageIndex, int pageSize, int totalCount) {
        return new PageBean(result, pageIndex, pageSize, (long) totalCount);
    }

    /**
     * 根据总数计算总页数
     *
     * @param totalCount 总数
     * @param pageSize   每页大小
     * @return 总页数
     * @since 5.8.5
     */
    private static int totalPage(long totalCount, int pageSize) {
        if (pageSize <= 0) {
            return 0;
        }
        return Math.toIntExact(totalCount % pageSize == 0 ? (totalCount / pageSize) : (totalCount / pageSize + 1));
    }

}
