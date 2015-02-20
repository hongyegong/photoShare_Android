package com.myou.appback.modules.exception;

/**
 * Description: 业务异常<br>
 * @version 1.0
 */
public class BusinessException extends RuntimeException {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 构造器
     * @param msg
     */
    public BusinessException(String msg) {
        super(msg);
    }
}