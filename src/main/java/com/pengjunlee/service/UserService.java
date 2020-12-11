package com.pengjunlee.service;

import com.pengjunlee.domain.User;

/**
 * @author pengjunlee
 * @create 2020-11-23 9:21
 */
public interface UserService {

    User getUserById(Long id);

    User updateUser(User user);

    void deleteUserById(Long id);
}
