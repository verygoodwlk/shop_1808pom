package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author ken
 * @Date 2019/1/16
 * @Version 1.0
 */
@Controller
public class IndexController {

    /**
     * 跳转到任意页面的处理请求
     * @param page
     * @return
     */
    @RequestMapping("/topage/{page}")
    public String topage(@PathVariable("page") String page){
        return page;
    }
}
