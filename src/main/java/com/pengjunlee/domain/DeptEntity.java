package com.pengjunlee.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pengjunlee
 * @create 2020-11-23 9:22
 */
@Data
public class DeptEntity extends BaseEntity {

    private String name;


    public DeptEntity() {
    }

    public DeptEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "DeptEntity{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                '}';
    }
}
