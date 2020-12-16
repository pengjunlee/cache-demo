package com.pengjunlee.service.impl;

import com.pengjunlee.domain.DeptEntity;
import com.pengjunlee.service.DeptService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author pengjunlee
 * @create 2020-11-23 9:24
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Override
    @Cacheable(cacheNames = {"dept_cache"}, keyGenerator = "deptKeyGenerator")
    public DeptEntity getDeptById(Long id) {
        System.out.println("调用方法：getDeptById ，参数：" + id);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new DeptEntity(id, "DeptEntity" + id);
    }

    @Override
    public DeptEntity getDeptByName(String name) {
        System.out.println("调用方法：getDeptByName ，参数：" + name);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new DeptEntity(1L, name);
    }
}
