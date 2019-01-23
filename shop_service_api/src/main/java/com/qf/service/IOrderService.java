package com.qf.service;

import com.qf.entity.Orders;
import com.qf.entity.User;

import java.util.List;

/**
 * @Author ken
 * @Time 2019/1/23 16:02
 * @Version 1.0
 */
public interface IOrderService {

    int addOrder(Integer aid, Integer[] cids, User user);

    List<Orders> queryByUid(Integer uid);
}
