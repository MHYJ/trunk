package com.mhyj.handler;

import com.mhyj.core.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author MHYJ
 * @date 2019/7/3 17:15
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception exception) {
        log.error("Exception:{}", exception.getLocalizedMessage());
        return Result.error("50001", exception.getMessage());
    }


    @ExceptionHandler(ArithmeticException.class)
    public Result exceptionHandler(ArithmeticException exception) {
        log.error("ArithmeticException:{}", exception.getLocalizedMessage());
        return Result.error("50002", exception.getMessage());
    }
}
