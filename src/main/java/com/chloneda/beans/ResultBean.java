package com.chloneda.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 接口标准返回格式
 *
 * @author chloneda
 * @see Page
 * @see PageBean
 * @see PageResultBean
 */
@Data
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = -450565530896878999L;

    private static final int successCode = ResultCode.SUCCESS.getCode();

    private static final String successMsg = ResultCode.SUCCESS.getMsg();

    /**
     * 状态码
     */
    private int code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public ResultBean() {
        this.code = successCode;
        this.msg = successMsg;
    }

    private ResultBean(T data) {
        this.code = successCode;
        this.msg = successMsg;
        this.data = data;
    }

    private ResultBean(int code, String msg) {
        this();
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    private ResultBean(int code, String msg, T data) {
        this();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultBean<T> ok() {
        return new ResultBean<T>(successCode, successMsg);
    }

    public static <T> ResultBean<T> ok(T data) {
        return new ResultBean<T>(successCode, successMsg, data);
    }

    public static <T> ResultBean<T> ok(int code, String msg, T data) {
        return new ResultBean<T>(code, msg, data);
    }

    public static <T> ResultBean<T> failed(IStatusCode statusCode) {
        return new ResultBean<T>(statusCode.getCode(), statusCode.getMsg());
    }

    public static <T> ResultBean<T> failed(IStatusCode statusCode, T message) {
        return new ResultBean<T>(statusCode.getCode(), statusCode.getMsg(), message);
    }

    public static <T> ResultBean<T> failed(String msg) {
        return new ResultBean<T>(ResultCode.FAILED.getCode(), msg);
    }

    public static <T> ResultBean<T> instance(int code, String msg, T data) {
        ResultBean<T> resultBean = new ResultBean<>();
        resultBean.setCode(code);
        resultBean.setMsg(msg);
        resultBean.setData(data);
        return resultBean;
    }

    public static <T> ResultBean<T> okIsEmpty(T obj) {
        if (isEmpty(obj)) {
            return new ResultBean<T>(ResultCode.SUCCESS.getCode(), "结果集为空", null);
        }
        return ResultBean.ok(obj);
    }

    public static <T> boolean isEmpty(T obj) {
        if (obj == null)
            return true;
        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();
        if (obj instanceof String)
            return obj.toString().replaceAll(" ", "").length() == 0;
        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;
        if (obj instanceof Map)
            return ((Map) obj).isEmpty();
        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }
}
