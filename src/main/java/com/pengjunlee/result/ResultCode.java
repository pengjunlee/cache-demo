package com.pengjunlee.result;

/**
 * @author pengjunlee
 * @create 2020-12-11 11:02
 */
public enum ResultCode {

    SUCCESS(200, "请求成功"), ERROR(500, "系统错误，请求失败");

    private Integer code;
    private String msg;


    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

}
