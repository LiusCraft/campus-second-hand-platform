package com.liuscraft.tradingplatform.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {
    SUCCESS(200, "成功", true),
    ERROR(500, "错误", false);
    private final Integer code;
    private final String message;
    private final Boolean status;

}
