package com.aoto.grains.common;

import lombok.Data;

/**
 * @ClassName Result
 * @Description
 * @Author huagu
 * @Date 2025/11/30 22:51
 * @Version 1.0
 **/
@Data
public class Result<T>{
    private Integer code;
    private String message;
    private T data;
    // 成功时的快捷方法
    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.code = 200;
        r.message = "success";
        r.data = data;
        return r;
    }
    // 失败时的快捷方法
    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.code = 500;
        r.message = msg;
        return r;
    }
}
