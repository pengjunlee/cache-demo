package com.pengjunlee.service;

import com.pengjunlee.domain.DeptEntity;

/**
 * @author pengjunlee
 * @create 2020-11-23 9:21
 */
public interface DeptService {

    DeptEntity getDeptById(Long id);

    DeptEntity getDeptByName(String name);
}
