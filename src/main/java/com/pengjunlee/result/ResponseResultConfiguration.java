package com.pengjunlee.result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author pengjunlee
 * @create 2020-12-11 14:08
 */
@Slf4j
@ControllerAdvice
public class ResponseResultConfiguration implements WebMvcConfigurer, ResponseBodyAdvice<Object> {
    // 标记名称
    public static final String RESPONSE_RESULT_ANN = "RESPONSE_RESULT_ANN";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ResponseResultInterceptor responseResultInterceptor = new ResponseResultInterceptor();
        registry.addInterceptor(responseResultInterceptor).addPathPatterns("/**");
    }

    // 是否请求包含了 需要包装注解标记，没有就直接返回，不需要重写返回体
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes sra = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = sra.getRequest();
        // 判断请求 是否有包装标记
        ResponseResult responseResult = (ResponseResult) request.getAttribute(RESPONSE_RESULT_ANN);
        return responseResult == null ? false : true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("进入返回体，重写格式处理中。。。");
        return Result.success(body);
    }
}
