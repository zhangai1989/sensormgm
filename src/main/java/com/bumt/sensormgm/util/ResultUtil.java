package com.bumt.sensormgm.util;


import com.bumt.sensormgm.common.pojo.Result;

/**
 * @Description: 前后交互工具类
 * @Author:     zhangai
 * @CreateDate: 2019/3/18 15:26
 * @Version: 1.0
 */
public class ResultUtil<T> {

    private Result<T> result;

    public ResultUtil(){
        result=new Result<T>();
        result.setSuccess(true);
        result.setMessage("success");
        result.setCode(Constant.ResultCode.SUCCESS);
    }

    public Result<T> setData(T t){
        this.result.setResult(t);
        this.result.setCode(Constant.ResultCode.SUCCESS);
        return this.result;
    }

    public Result<T> setData(T t, String msg){
        this.result.setResult(t);
        this.result.setCode(Constant.ResultCode.SUCCESS);
        this.result.setMessage(msg);
        return this.result;
    }

    public Result<T> setErrorMsg(String msg){
        this.result.setSuccess(false);
        this.result.setMessage(msg);
        this.result.setCode(Constant.ResultCode.ERROR);
        return this.result;
    }

    public Result<T> setErrorMsg(Integer code, String msg){
        this.result.setSuccess(false);
        this.result.setMessage(msg);
        this.result.setCode(code);
        return this.result;
    }
}
