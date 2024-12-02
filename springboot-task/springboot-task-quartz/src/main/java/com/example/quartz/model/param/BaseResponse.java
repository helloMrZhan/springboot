package com.example.quartz.model.param;

/**
 * 通用返回
 * @author zjq
 */
public class BaseResponse {
    /**
     * 状态码
     * 0成功，1失败
     */
    private int code;
    /**
     * 描述信息
     * 成功，或具体错误信息
     */
    private String msg;
    /**
     * 业务数据
     */
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static BaseResponse custom(int code ,String msg ,Object data){
        BaseResponse response = new BaseResponse();
        response.setCode(code);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }

    public static BaseResponse success(String msg,Object data){
        return custom(MsgCode.SUCCESS.code,msg,data);
    }

    public static BaseResponse success(){
        return success(MsgCode.SUCCESS.desc,null);
    }

    public static BaseResponse success(Object data){
        BaseResponse response = success();
        response.setData(data);
        return response;
    }

    public static BaseResponse fail(int code,String msg){
        return custom(code,msg,null);
    }

    public static BaseResponse fail(String msg){
        return fail(MsgCode.FAILED.code,msg);
    }

    public static BaseResponse fail(String msg,Object data){
        return custom(MsgCode.FAILED.code,msg,data);
    }
}