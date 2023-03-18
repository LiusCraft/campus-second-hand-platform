package com.liuscraft.tradingplatform.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.liuscraft.tradingplatform.utils.R;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiusCraft
 * @date 2023/3/10 10:07
 */
@AllArgsConstructor
@Getter
public enum OrderStatus {
    /**
     * 取消订单
     */
    WAIT(0, "取消订单"),
    /**
     * 买家已收货
     */
    FINISH(1, "买家已收货"),
    /**
     * 等待确认订单信息
     */
    NO_APPLY(2,"等待确认订单信息"),
    /**
     * 已确认订单信息
     */
    APPLY(3, "已确认订单信息"),
    /**
     * 等待发货
     */
    WAIT_DELIVER(4, "等待发货"),
    /**
     * 已发货
     */
    DELIVER(5, "已发货");
    Integer code;
    String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
    @JsonValue
    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("code",getCode());
        map.put("msg",getMsg());
        return map;
    }
    public static OrderStatus getStatusByCode(Integer code){
        if (code==null)return null;
        for (OrderStatus value : OrderStatus.values()) {
            if (value.code.intValue() == code.intValue()) {
                return value;
            }
        }
        return null;
    }
}
