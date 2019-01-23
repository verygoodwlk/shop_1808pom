package com.qf.service;

import com.qf.entity.Cart;
import com.qf.entity.User;

import java.util.List;

/**
 * @Author ken
 * @Time 2019/1/23 9:22
 * @Version 1.0
 */
public interface ICartService {

    String addCart(Cart cart, User user, String cartToken);

    int mergeCart(String cartToken, User user);

    List<Cart> queryCartList(String cartToken, User user);
}
