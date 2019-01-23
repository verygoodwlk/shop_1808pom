package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.commons.aop.IsLogin;
import com.qf.entity.Address;
import com.qf.entity.Cart;
import com.qf.entity.Orders;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import com.qf.service.ICartService;
import com.qf.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author ken
 * @Date 2019/1/23
 * @Version 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference
    private IAddressService addressService;

    @Reference
    private ICartService cartService;

    @Reference
    private IOrderService orderService;

    /**
     * 订单编辑
     * 参数：勾选的购物车的商品id数组
     * @return
     */
    @IsLogin(tologin = true)
    @RequestMapping("/edit")
    public String edit(Integer[] gid, User user, Model model){

        //根据登录用户，获取该用户所有的收货地址
        List<Address> addresses = addressService.queryAddressByUid(user.getId());

        //根据勾选的商品id，找出对应的购物车信息
        //所有购物车数据
        List<Cart> carts = cartService.queryCartList(null, user);
        //需要下单的购物车数据
        List<Cart> carts2 = new ArrayList<>();

        for(Cart cart : carts){
            for(Integer id : gid){
                if(cart.getGid() == id){
                    carts2.add(cart);
                }
            }
        }


        model.addAttribute("addresses", addresses);
        model.addAttribute("carts", carts2);

        return "orderedit";
    }

    /**
     * 添加订单
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    @IsLogin
    public int insertOrder(Integer aid, @RequestParam("cids[]") Integer[] cids, User user){

        System.out.println("收货地址：" + aid + " 购物车列表：" + Arrays.toString(cids));

        return orderService.addOrder(aid, cids, user);
    }

    /**
     * 查询所有订单
     * @return
     */
    @RequestMapping("/showlist")
    @IsLogin(tologin = true)
    public String showList(User user, Model model){
        List<Orders> orders = orderService.queryByUid(user.getId());
        model.addAttribute("orders", orders);
        return "orderlist";
    }
}
