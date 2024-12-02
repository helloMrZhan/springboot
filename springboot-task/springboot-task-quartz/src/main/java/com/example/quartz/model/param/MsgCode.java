package com.example.quartz.model.param;

public enum MsgCode {

    SUCCESS(0,"成功"),
    FAILED(1,"失败"),
    NO_LOGIN(401,"失败"),

    ;

    public int code;
    public String desc;

    MsgCode(int code, String desc){
        this.code = code;
        this.desc = desc;
    }
}