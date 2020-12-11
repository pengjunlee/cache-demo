package com.pengjunlee.result;

import java.lang.annotation.*;

/**
 * @author pengjunlee
 * @create 2020-12-11 13:52
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ResponseResult {
}
