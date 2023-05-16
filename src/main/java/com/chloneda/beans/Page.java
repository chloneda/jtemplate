package com.chloneda.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 接口简单分页标准格式，返回结果集和记录总数
 *
 * @author chloneda
 * @see PageBean
 * @see ResultBean
 * @see PageResultBean
 */
@Data
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_PAGE_INDEX = 1;

    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 记录总数
     */
    private long totalCount;

    /**
     * 数据集
     */
    private List<T> result;

    private Page() {
    }

    public Page(List<T> result, long totalCount) {
        this();
        this.totalCount = totalCount;
        this.result = result;
    }

    public Page(List<T> result, int totalCount) {
        this(result, (long) totalCount);
    }

    public static <T> Page<T> page(List<T> result, long totalCount) {
        return new Page(result, totalCount);
    }

    public static <T> Page<T> page(List<T> result, int totalCount) {
        return new Page(result, totalCount);
    }

}
