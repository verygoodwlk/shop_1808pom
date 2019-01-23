package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.qf.commons.aop.IsLogin;
import com.qf.entity.Cart;
import com.qf.entity.User;
import com.qf.service.ICartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author ken
 * @Date 2019/1/22
 * @Version 1.0
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Reference
    private ICartService cartService;

    /**
     * 添加购物车 - AOP
     * @return
     */
    @IsLogin
    @RequestMapping("/add")
    public String addCart(
            @CookieValue(value = "cart_token", required = false) String cartToken,
            Cart cart,
            User user,
            HttpServletResponse response){

        //调用服务添加购物车
        String cToken = cartService.addCart(cart, user, cartToken);

        //回写cookie
        if(cartToken == null){
            Cookie cookie = new Cookie("cart_token", cToken);
            cookie.setMaxAge(60 * 60 * 24 * 365);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return "succ";
    }

    /**
     * 前端展示购物车数据
     * @return
     */
    @RequestMapping("/showlist")
    @IsLogin
    @ResponseBody
    public String showlist(@CookieValue(value = "cart_token", required = false) String cartToken,
                           User user){

        List<Cart> carts = cartService.queryCartList(cartToken, user);

        return "cartlist(" + new Gson().toJson(carts) + ")";
    }


    /**
     * 跳转到购物车工程列表
     * @return
     */
    @IsLogin
    @RequestMapping("/cartlist")
    public String cartlist(@CookieValue(value = "cart_token", required = false) String cartToken,
                           User user, Model model){

        List<Cart> carts = cartService.queryCartList(cartToken, user);
        model.addAttribute("carts", carts);
        System.out.println("--->" + carts);

        return "cartlist";
    }
}
