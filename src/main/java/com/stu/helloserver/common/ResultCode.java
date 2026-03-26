package com.stu.helloserver.common;

public enum ResultCode {
    SUCCESS(200,"操作成功"),
    ERROR(500,"系统繁忙，请稍后再试"),
    TOKEN_INVALID(401,"登录凭证已确实或过期，请重新登录"),
    USER_HAS_EXISTED(4001,"该用户名已注册"),
    USER_NOT_EXIST(4002,"该用户不存在"),
    PASSWORD_ERROR(4003,"账号或密码错误");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
