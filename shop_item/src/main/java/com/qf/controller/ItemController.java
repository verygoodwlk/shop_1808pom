package com.qf.controller;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ken
 * @Date 2019/1/18
 * @Version 1.0
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private Configuration configuration;

    @RequestMapping("/createhtml")
    @ResponseBody
    public String  createHtml(@RequestBody Goods goods)  {

        System.out.println("--->" + goods);

        //json -> goods对象
        Map<String, Goods> data = new HashMap<>();
        data.put("goods", goods);

        //准备输出流
        //获得classpath路径
        String path = this.getClass().getResource("/static/page/").getPath() + goods.getId() + ".html";
        System.out.println("静态页的生成路径：" + path);

        try(
                Writer out = new FileWriter(path);
        ) {
            //准备商品详情的模板
            Template template = configuration.getTemplate("goods.ftl");
            //生成静态页
            template.process(data, out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "ok";
    }


//    public void test(){
//        //准备模板
//        Template template = configuration.getTemplate("hello.ftl");
//
//        //准备数据
//        Map map = new HashMap();
//        map.put("name", "Freemarker!!!!");
//        map.put("age", 29);
//
//        String[] names = {"小明", "小红", "小刚"};
//        map.put("names", names);
//
//        map.put("time", new Date());
//
//        //inputstrem outputstream reader writer
//        //组合生成静态页
//        Writer out = new FileWriter("C:\\Users\\ken\\Desktop\\hello.html");
//        template.process(map, out);
//        out.close();
//    }
}
