package com.liuscraft.tradingplatform.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author haohao-senior
 */
@Data
public class R {
    private Integer code;
    private Boolean state;
    private String msg;
    private Map<String,Object> data = new HashMap<>();

    //私有化
    private R(){};

//    成功放回结果：
    public static  R ok(){
        return R.builder(ResultCode.SUCCESS);
    }

    public static R builder(ResultCode resultCode) {
        R r = new R();
        r.code(resultCode.getCode()).state(resultCode.getStatus()).msg(resultCode.getMessage());
        return r;
    }

    //   失败放回结果：
    public static  R error(){
        return R.builder(ResultCode.ERROR);
    }
//    设置字段值
    public R state(Boolean state){
        this.setState(state);
        return this;
    }
    public R msg(String msg){
        this.setMsg(msg);
        return this;
    }
    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String , Object> data){
        this.setData(data);
        return this;
    }

    public <T> T getMapData(String key, Class<T> klass) {
        Object o = data.get(key);
        if (o == null) return null;
        return klass.cast(o);
    }

}