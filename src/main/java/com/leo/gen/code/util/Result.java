/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.leo.gen.code.util;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回数据
 *
 * @author leo
 */
@Data
public class Result<T> implements Serializable {

    private int code;

    private String message;

    private T data;

    public static final int SUCCESS_CODE = 200;

    public static final int ERROR_CODE = 500;

    public static final String SUCCESS_MESSAGE = "success";

    public static final String ERROR_MESSAGE = "未知异常,请联系管理员";

    public Result() {
        this.code = SUCCESS_CODE;
        this.message = SUCCESS_MESSAGE;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, T t) {
        this.code = code;
        this.message = message;
        this.data = t;
    }

    public static <T> Result<T> instance(int code, String message, T t) {
        return new Result<T>(code, message, t);
    }

    public static <T> Result<T> error() {
        return error(ERROR_MESSAGE);
    }

    public static <T> Result<T> error(String msg) {
        return error(ERROR_CODE, msg);
    }

    public static <T> Result<T> error(T t) {
        return instance(ERROR_CODE, ERROR_MESSAGE, t);
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result<T>(code, msg);
    }

    public static <T> Result<T> ok() {
        return ok(SUCCESS_MESSAGE);
    }

    public static <T> Result<T> ok(String msg) {
        return ok(SUCCESS_CODE, msg);
    }

    public static <T> Result<T> ok(T t) {
        return instance(SUCCESS_CODE, SUCCESS_MESSAGE, t);
    }

    public static <T> Result<T> ok(int code, String msg) {
        return new Result<T>(code, msg);
    }

}
