package com.qf.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ken
 * @Date 2019/1/16
 * @Version 1.0
 */
@Data
public class Goods implements Serializable {

    private int id;
    private String title;
    private String ginfo;
    private int gcount;
    private int tid;//分类表的外键
    private double allprice;
    private double price;
    private String gimage;
}
