package com.wq.shop.example.exception;

/**
 * @Author: wangqiang20995
 * @Date: 2020/4/29 18:12
 * @Description:
 **/
public class KillException extends RuntimeException {

    private String errorCode;
    private String errorInfo;

    public KillException(String code, String err) {
        super(code + "/" + err);
        this.errorCode = code;
        this.errorInfo = err;
    }
}
