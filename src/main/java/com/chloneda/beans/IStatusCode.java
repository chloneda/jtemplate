package com.chloneda.beans;

/**
 * 标准状态码接口
 *
 * @author chloneda
 */
public interface IStatusCode {

    /**
     * @return 状态码
     */
    public int getCode();

    /**
     * @return 响应信息
     */
    public String getMsg();

}
