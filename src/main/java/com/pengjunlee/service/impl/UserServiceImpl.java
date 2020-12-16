package com.pengjunlee.service.impl;

import com.pengjunlee.domain.UserEntity;
import com.pengjunlee.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author pengjunlee
 * @create 2020-11-23 9:24
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserEntity getUserById(Long id) {
        System.out.println("调用方法：getUserById ，参数：" + id);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new UserEntity(id, "UserEntity" + id);
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        System.out.println("更新用户：" + user);
        return user;
    }

    @Override
    public void deleteUserById(Long id) {
        System.out.println("删除用户ID：" + id);
    }
}
