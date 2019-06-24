package com.bumt.sensormgm.common.pojo;


import com.bumt.sensormgm.common.enums.ResultStatusCode;

import java.io.Serializable;

/**
 * @Description: 前后端交互数据标准
 * @Author:     zhangai
 * @CreateDate: 2019/3/18 15:27
 * @Version: 1.0
 */
public class Result<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    private boolean success;

    /**
     * 失败消息
     */
    private String message;

    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 结果对象
     */
    private T result;

    public Result(int code, String message, T result){
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public Result(ResultStatusCode resultStatusCode, T result){
        this(resultStatusCode.getCode(), resultStatusCode.getMsg(), result);
    }

    public Result(int code, String msg){
        this(code, msg, null);
    }

    public Result(ResultStatusCode resultStatusCode){
        this(resultStatusCode, null);
    }

    public Result(){
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
