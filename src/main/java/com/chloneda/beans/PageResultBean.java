package com.chloneda.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 标准分页返回格式
 *
 * @author chloneda
 * @see Page
 * @see PageBean
 * @see ResultBean
 */
@Data
public class PageResultBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int successCode = ResultCode.SUCCESS.getCode();

    private static final String successMsg = ResultCode.SUCCESS.getMsg();

    /**
     * 当前页数，从 1 开始计数
     */
    private int pageIndex;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 记录总数
     */
    private long totalCount;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 状态码
     */
    private int code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private List<T> data;

    private PageResultBean() {
        this.code = successCode;
        this.msg = successMsg;
    }

    private PageResultBean(List<T> data) {
        this.code = successCode;
        this.msg = successMsg;
        this.data = data;
    }

    private PageResultBean(int code, String msg) {
        this();
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    private PageResultBean(int code, String msg, List<T> data, long totalCount) {
        this();
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.totalCount = totalCount;
    }

    private PageResultBean(int code, String msg, List<T> data, int pageIndex, int pageSize) {
        this();
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    private PageResultBean(int code, String msg, List<T> data, int pageIndex, int pageSize, long totalCount) {
        this();
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = pageSize == 0 ? 0 : Math.toIntExact(totalCount % (long) pageSize == 0L ? totalCount / (long) pageSize : totalCount / (long) pageSize + 1L);
    }

    public static <T> PageResultBean<T> ok() {
        return new PageResultBean<T>(successCode, successMsg);
    }

    public static <T> PageResultBean<T> ok(List<T> data, long totalCount) {
        return new PageResultBean<T>(successCode, successMsg, data, totalCount);
    }

    public static <T> PageResultBean<T> ok(List<T> data, int totalCount) {
        return new PageResultBean<T>(successCode, successMsg, data, totalCount);
    }

    public static <T> PageResultBean<T> ok(List<T> data, int pageIndex, int pageSize) {
        return new PageResultBean<T>(successCode, successMsg, data, pageIndex, pageSize);
    }

    public static <T> PageResultBean<T> ok(List<T> data, int pageIndex, int pageSize, long totalCount) {
        return new PageResultBean<T>(successCode, successMsg, data, pageIndex, pageSize, totalCount);
    }

    public static <T> PageResultBean<T> ok(List<T> data, int pageIndex, int pageSize, int totalCount) {
        return new PageResultBean<T>(successCode, successMsg, data, pageIndex, pageSize, totalCount);
    }

    public static <T> PageResultBean<T> failed(IStatusCode statusCode) {
        return new PageResultBean<T>(statusCode.getCode(), statusCode.getMsg());
    }

    public static <T> PageResultBean<T> failed(int code, String msg) {
        return new PageResultBean<T>(code, msg);
    }

    public static <T> PageResultBean<T> failed(String msg) {
        return new PageResultBean<T>(ResultCode.FAILED.getCode(), msg);
    }

    public static <T> PageResultBean<T> instance(int code, String msg, List<T> data, int pageIndex, int pageSize, long totalCount) {
        PageResultBean<T> pageResultBean = new PageResultBean<>();
        pageResultBean.setCode(code);
        pageResultBean.setMsg(msg);
        pageResultBean.setData(data);
        pageResultBean.setPageIndex(pageIndex);
        pageResultBean.setPageSize(pageSize);
        pageResultBean.setTotalCount(totalCount);
        return pageResultBean;
    }

}
