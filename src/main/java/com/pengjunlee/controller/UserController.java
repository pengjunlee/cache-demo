package com.pengjunlee.controller;

import com.pengjunlee.domain.UserEntity;
import com.pengjunlee.result.ResponseResult;
import com.pengjunlee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spel表达式
 * 名称	          位置	    描述                 	                                                                    示例
 * methodName	  root对象	当前被调用的方法名	                                                                        #root.methodName
 * method	      root对象	当前被调用的方法	                                                                            #root.method.name
 * target	      root对象	当前被调用的目标对象                                                                         #root.target
 * targetClass	  root对象	当前被调用的目标对象类	                                                                    #root.targetClass
 * args	          root对象	当前被调用的方法的参数列表	                                                                #root.args[0]
 * caches	      root对象	当前方法调用使用的缓存列表(如@Cacheable(value = {"user_cache,default_cache"})则有两个cache)    #root.caches[0].name
 * Argument Name  执行上下文	方法参数的名字. 可以直接 #参数名 ，也可以使用 #p0或#a0 的形式，0代表参数的索引	                #artsian.id #id #a0 #p0
 * result	      执行上下文	方法执行后的返回值（仅当方法执行之后的判断有效，如‘unless’，’cache put’的表达式 ’cache evict’的表达式beforeInvocation=false）	#result
 * <p>
 * <p>
 * condition成立的条件缓存，unless成立的条件不缓存。以下实例含义为：id > 1 且 id != 2 的缓存
 * 1、condition:id >1的会被缓存，只执行一次查询SQL，id <= 1的每次查询都会执行SQL，#a0是取第一个参数，也可以用“#参数名” 即：#id
 * 2、unless: id==2的不缓存
 * 3、sync: 异步方式缓存 sync = true 可以有效的避免缓存击穿的问题，多线程环境下只有一个线程查询数据库，其他线程将等待。
 * sync 和 unless 不能同时支持，否则会报错
 */

/**
 * @author pengjunlee
 * @create 2020-11-23 9:42
 */
@RestController
@RequestMapping("/user")
@ResponseResult
@CacheConfig(cacheNames = {"user_cache"})
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    @Cacheable(key = "T(String).valueOf('user').concat(#id)", unless = "#id==0")
    public Object getUser(@RequestParam(name = "id", required = true) Long id) {
        return userService.getUserById(id);
    }

    /**
     * @param user
     * @return
     * @CachePut：既调用方法，又更新缓存数据；同步更新缓存 修改了数据库的某个数据，同时更新缓存；
     * 运行时机：
     * 1、先调用目标方法
     * 2、将目标方法的结果缓存起来
     */
    @RequestMapping("/update")
    @CachePut(key = "T(String).valueOf('user').concat(#user.id)", condition = "#id>0")
    public Object updateUser(UserEntity user) {
        return userService.updateUser(user);
    }

    /**
     * @param id
     * @return
     * @CacheEvict：缓存清除 key：指定要清除的数据
     * allEntries = true：指定清除这个缓存中所有的数据
     * beforeInvocation = false：缓存的清除是否在方法之前执行 默认代表缓存清除操作是在方法执行之后执行;如果出现异常缓存就不会清除
     * beforeInvocation = true：代表清除缓存操作是在方法运行之前执行，无论方法是否出现异常，缓存都清除
     */
    @RequestMapping("/delete")
    @CacheEvict(key = "T(String).valueOf('user').concat(#id)", beforeInvocation = false)
    public void deleteUser(@RequestParam(name = "id", required = true) Long id) {
        userService.deleteUserById(id);
    }

    @RequestMapping("/error")
    public Object error(@RequestParam(name = "id", required = true) Long id) {
        if (id == 0) {
            throw new RuntimeException();
        }
        return userService.getUserById(id);
    }
}


