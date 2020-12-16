package com.pengjunlee.result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常捕获
 */
//@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    // 标记名称
    public static final String RESPONSE_RESULT_ANN = "RESPONSE_RESULT_ANN";

    /**
     * 捕获全局异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandle(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        request.removeAttribute(RESPONSE_RESULT_ANN);
        return Result.error();
    }

}
