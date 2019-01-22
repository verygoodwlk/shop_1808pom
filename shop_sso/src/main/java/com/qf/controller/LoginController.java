package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author ken
 * @Date 2019/1/22
 * @Version 1.0
 */
@Controller
@RequestMapping("/sso")
public class LoginController {

    @Reference
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 去到登录页
     * @return
     */
    @RequestMapping("/tologin")
    public String toLogin(){
        return "login";
    }


    /**
     * 进行登录
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, HttpServletResponse response, Model model){

        //调用登录的服务
        User user = userService.queryByUserNameAndPassword(username, password);

        if(user != null){
            //登录成功

            //记录登录状态
            String uuid = UUID.randomUUID().toString();
            //以uuid为key，用户信息为value，保存进redis中
            redisTemplate.opsForValue().set(uuid, user);

            //回写cookie到用户的浏览器
            Cookie cookie = new Cookie("login_token", uuid);
            cookie.setMaxAge(60 * 60 * 24 * 30);
//            cookie.setPath("");
//            cookie.setDomain("");
//            cookie.setHttpOnly();
//            cookie.setSecure();
            response.addCookie(cookie);

            return "redirect:http://localhost:8082";
        }

        model.addAttribute("error", "用户名或者密码错误！");
        return "login";
    }


    /**
     * 验证是否登录
     * @return
     */
    @RequestMapping("/islogin")
    @ResponseBody
//    @CrossOrigin
    public String isLogin(@CookieValue(value = "login_token", required = false) String token){
        //获得用户浏览器中的cookie里的uuid
        System.out.println("获得用户浏览器中的token值：" + token);

        User user = null;
        if(token != null){
            //通过uuid验证redis中是否有用户信息
            user = (User) redisTemplate.opsForValue().get(token);
        }

        //如果有用户信息直接返回，没有这表示没有登录
        return user != null ? "islogin(" + new Gson().toJson(user) + ")" : "islogin(null)";
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping("/logout")
    public String logout(@CookieValue(value = "login_token", required = false) String token, HttpServletResponse response){

        //删除redis
        redisTemplate.delete(token);

        //删除cookie
        Cookie cookie = new Cookie("login_token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "login";

    }
}
