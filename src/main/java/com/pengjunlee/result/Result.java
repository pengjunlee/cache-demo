package com.pengjunlee.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pengjunlee
 * @create 2020-12-11 11:25
 */
@Data
public class Result implements Serializable {

    private Integer code;

    private String msg;

    private Object data;

    public Result() {
    }

    public Result(ResultCode resultCode, Object data) {
        this.code = resultCode.code();
        this.msg = resultCode.msg();
        this.data = data;
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.code();
        this.msg = resultCode.msg();
    }

    public static Result success() {
        Result result = new Result(ResultCode.SUCCESS);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result(ResultCode.SUCCESS, data);
        return result;
    }

    public static Result error() {
        Result result = new Result(ResultCode.ERROR);
        return result;
    }
}
