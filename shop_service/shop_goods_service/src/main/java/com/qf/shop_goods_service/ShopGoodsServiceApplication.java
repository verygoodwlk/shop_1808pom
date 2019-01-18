package com.qf.shop_goods_service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.qf")
@MapperScan("com.qf.dao")
@DubboComponentScan("com.qf.serviceimpl")
@EnableTransactionManagement
public class ShopGoodsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopGoodsServiceApplication.class, args);
    }

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue getQueue(){
        return new Queue("goods_queue");
    }

    /**
     * 声明交换机
     * @return
     */
    @Bean
    public FanoutExchange getExchange(){
        return new FanoutExchange("goods_exchange");
    }

    /**
     * 绑定交换机和队列
     * @param getQueue
     * @param getExchange
     * @return
     */
    @Bean
    public Binding getExchangeBinding(Queue getQueue, FanoutExchange getExchange){
        return BindingBuilder.bind(getQueue).to(getExchange);
    }
}

