package com.pengjunlee.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pengjunlee
 * @create 2020-12-16 10:30
 */
@Data
public class BaseEntity implements Serializable {

    protected Long id;

    protected String createBy;

    protected Date createTime;

    protected String updatedBy;

    protected Date updatedTime;


}
