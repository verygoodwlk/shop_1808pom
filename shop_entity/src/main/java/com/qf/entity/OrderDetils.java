package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ken
 * @Date 2019/1/23
 * @Version 1.0
 * OrderDetils -> order_detils
 */
@Data
@TableName("orderdetils")
public class OrderDetils implements Serializable {

    private int id;
    private int oid;
    private int gid;
    private String gname;
    private String ginfo;
    private int gcount;
    private double price;
    private String gimage;
}
