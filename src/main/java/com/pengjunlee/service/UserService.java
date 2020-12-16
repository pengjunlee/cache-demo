package com.pengjunlee.service;

import com.pengjunlee.domain.UserEntity;

/**
 * @author pengjunlee
 * @create 2020-11-23 9:21
 */
public interface UserService {

    UserEntity getUserById(Long id);

    UserEntity updateUser(UserEntity user);

    void deleteUserById(Long id);
}
