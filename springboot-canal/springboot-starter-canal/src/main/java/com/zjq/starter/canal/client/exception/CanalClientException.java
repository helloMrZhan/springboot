package com.zjq.starter.canal.client.exception;

/**
 * canal 操作的异常类
 *
 * @author
 * @created 2020/5/28 16:39
 * @Modified_By 阿导 2020/5/28 16:39
 */
public class CanalClientException extends RuntimeException {

    /**
     * 默认构造方法
     *
     * @param
     * @return
     * @time 2020/5/28 16:39
     */
    public CanalClientException() {
    }

    /**
     * 带错误信息的构造方法
     *
     * @param message
     * @return
     * @time 2020/5/28 16:39
     */
    public CanalClientException(String message) {
        super(message);
    }

    /**
     * 带错误信息和其造成原因的构造方法
     *
     * @param message
     * @param cause
     * @return
     * @time 2020/5/28 16:39
     */
    public CanalClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 带造成错误信息的构造方法
     *
     * @param cause
     * @return
     * @time 2020/5/28 16:43
     */
    public CanalClientException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     * @return
     * @time 2020/5/28 16:43
     */
    public CanalClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
