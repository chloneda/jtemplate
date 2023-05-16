package com.chloneda.exception;

import com.chloneda.beans.IStatusCode;
import com.chloneda.beans.ResultCode;

/**
 * 自定义异常
 *
 * @author chloneda
 */
public class BusinessException extends RuntimeException {

    private int code;

    private String msg;

    private IStatusCode statusCode;

    /**
     * 设置自定义异常
     */
    public BusinessException(IStatusCode statusCode, String message) {
        // message用于用户设置抛出自定义错误详情
        super(message);
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.statusCode = statusCode;
    }

    /**
     * 默认异常
     */
    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.FAILED.getCode();
        this.msg = ResultCode.FAILED.getMsg();
        this.statusCode = ResultCode.FAILED;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return this.msg;
    }

    public IStatusCode getStatusCode() {
        return this.statusCode;
    }

}
