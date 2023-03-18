package com.liuscraft.tradingplatform.handler;

import com.liuscraft.tradingplatform.utils.R;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LiusCraft
 * @date 2023/3/13 0:20
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public R handler(Exception e) {
        e.printStackTrace();
        return R.error().msg(e.getMessage());
    }
}
