package com.pengjunlee.service.impl;

import com.pengjunlee.domain.User;
import com.pengjunlee.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author pengjunlee
 * @create 2020-11-23 9:24
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUserById(Long id) {
        System.out.println("调用方法：getUserById ，参数：" + id);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new User(id, "User" + id);
    }

    @Override
    public User updateUser(User user) {
        System.out.println("更新用户：" + user);
        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        System.out.println("删除用户ID：" + id);
    }
}
