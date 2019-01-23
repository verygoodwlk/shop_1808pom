package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 一条购物车的记录
 * 整个购物车 - List<Cart>
 *
 * @Author ken
 * @Date 2019/1/23
 * @Version 1.0
 */
@Data
@TableName("shopcart")
public class Cart implements Serializable {

    private int id;
    private int gid;//商品id
    private int gnumber;//商品的数量
    private int uid;//所属用户

    @TableField(exist = false)
    private Goods goods;

}
