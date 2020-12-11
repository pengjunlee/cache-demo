package com.pengjunlee.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pengjunlee
 * @create 2020-11-23 9:22
 */
@Data
public class User implements Serializable {

    private Long id;
    private String name;
    private Dept dept;

    public User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(Long id, String name, Dept dept) {
        this.id = id;
        this.name = name;
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
