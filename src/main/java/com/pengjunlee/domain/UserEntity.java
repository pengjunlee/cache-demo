package com.pengjunlee.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pengjunlee
 * @create 2020-11-23 9:22
 */

@Data
public class UserEntity extends BaseEntity {

    private String name;

    private DeptEntity dept;

    public UserEntity() {
    }

    public UserEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserEntity(Long id, String name, DeptEntity dept) {
        this.id = id;
        this.name = name;
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                '}';
    }
}
