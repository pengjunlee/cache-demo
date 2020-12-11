package com.pengjunlee.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pengjunlee
 * @create 2020-11-23 9:22
 */
@Data
public class Dept implements Serializable {

    private Long id;
    private String name;


    public Dept() {
    }

    public Dept(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
