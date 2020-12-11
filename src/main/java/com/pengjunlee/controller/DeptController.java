package com.pengjunlee.controller;

import com.pengjunlee.service.DeptService;
import com.pengjunlee.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengjunlee
 * @create 2020-11-23 9:42
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/getbyid")
    public Object getDeptById(@RequestParam(name = "id", required = true) Long id) {
        return deptService.getDeptById(id);
    }

    @RequestMapping("/getbyname")
    /**
     * 复杂的缓存规则：
     * 1.以name查询还会去查询数据库：因为有@CachePut注解，所以这方法一定要执行的，@CachePut把方法执行的结果缓存到缓存
     * 2. (1), (1) : 每次都会执行SQL，因为有@CachePut
     *   （1）,（2）: （1）执行SQL，不执行
     *
     *
     * */
    @Caching(
            cacheable = {
                    @Cacheable(cacheNames = {"dept_cache"}, key = "'dept|'+#name") //（1）根据name查询user
            },
            put = {
                    @CachePut(cacheNames = {"dept_cache"}, key = "'dept|'+#result.id") //(2) 根据id查询user 以另一种key将查询出的结果缓存到缓存中
            }
    )
    public Object getDeptByName(@RequestParam(name = "name", required = true) String name) {
        return deptService.getDeptByName(name);
    }
}
