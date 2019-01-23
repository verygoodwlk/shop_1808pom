package com.qf.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ken
 * @Date 2019/1/23
 * @Version 1.0
 */
@Data
public class Address implements Serializable {

    private int id;
    private String person;
    private String address;
    private String phone;
    private int code;
    private int uid;
    private int isdefault = 0;
}
