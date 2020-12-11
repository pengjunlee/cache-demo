package com.pengjunlee.service;

import com.pengjunlee.domain.Dept;

/**
 * @author pengjunlee
 * @create 2020-11-23 9:21
 */
public interface DeptService {

    Dept getDeptById(Long id);

    Dept getDeptByName(String name);
}
