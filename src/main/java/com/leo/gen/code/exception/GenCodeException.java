package com.leo.gen.code.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 *
 * @author leo
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GenCodeException extends RuntimeException {

    private String msg;
    private int code = 500;

    public GenCodeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public GenCodeException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public GenCodeException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public GenCodeException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}