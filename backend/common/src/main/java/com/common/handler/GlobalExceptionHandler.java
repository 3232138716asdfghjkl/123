package com.common.handler;



import com.common.exception.KnownException;
import com.common.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler
    public Result exceptionHandler(Exception ex){
        log.error(ex.getMessage(), ex);
        return Result.error("服务器未知错误");
    }
    @ExceptionHandler(KnownException.class)
    public Result knownExceptionHandle(KnownException ex) {
        log.error(ex.getMessage(), ex);
        return Result.error(ex.getMessage());
    }
}
