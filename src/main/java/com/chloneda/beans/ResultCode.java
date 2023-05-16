package com.chloneda.beans;

/**
 * 系统返回编码
 *
 * @author chloneda
 */
public enum ResultCode implements IStatusCode {
    SUCCESS(0, "请求成功"),
    FAILED(500, "请求失败"),
    DATA_EMPTY(201, "结果集为空"),
    NETWORK_TIME_OUT(301, "网络请求超时"),
    NETWORK_ERROR(314, "网络请求错误"),
    MISSING_PARAM(400, "缺少参数"),
    ERROR_PARAM(401, "参数有误"),
    BAD_REQUEST(403, "错误请求方式"),
    NOT_FOUND(404, "Not Found"),
    ERROR_DATE_FORMAT(405, "日期格式错误");

    private int code;

    private String msg;

    ResultCode(int code, String msg){
        this.code= code;
        this.msg=msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

}
