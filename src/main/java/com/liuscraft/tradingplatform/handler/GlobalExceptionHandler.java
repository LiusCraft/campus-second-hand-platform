package com.liuscraft.tradingplatform.handler;

import com.liuscraft.tradingplatform.utils.R;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author LiusCraft
 * @date 2023/3/13 0:20
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    public R handleValidationException(BindException ex) {
        R result = R.error();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            result.data(error.getField(), error.getDefaultMessage());
        }
        Optional<Object> firstError = result.getData().values().stream().findFirst();
        return result.msg("请完善信息: "+firstError.orElse("表单内容") +
                (ex.getErrorCount()>1?" 等多项问题":""));
    }
    @ExceptionHandler
    @ResponseBody
    public R handler(Exception e) {
        e.printStackTrace();
        return R.error().msg(e.getMessage());
    }
}
