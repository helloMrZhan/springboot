package com.zjq.upload.base.exception;


import com.zjq.upload.base.comon.ResponseInfo;
/**
 * @author zjq
 * @date 2021/9/29 21:50
 * <p>title:业务异常</p>
 * <p>description:</p>
 */
public class BusinessException extends Exception  {
    private static final long serialVersionUID = -778142600038732285L;
    private String message;
    private String code;

    public BusinessException(String message) {
        this.message = message;
        this.code = ResponseInfo.CODE_ERROR;
    }

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public ResponseInfo<Object> getResponse() {
        return new ResponseInfo<Object>(code, message, null);
    }
}
