package com.qf.listener;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ken
 * @Date 2019/1/18
 * @Version 1.0
 */
@Component
@RabbitListener(queues = "goods_queue")
public class MyRabbitListener {

    @Autowired
    private Configuration configuration;

    @RabbitHandler
    public void handlerMsg(Goods goods){
        System.out.println("接收到消息：" + goods);
        //生成静态页面
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
    }
}
