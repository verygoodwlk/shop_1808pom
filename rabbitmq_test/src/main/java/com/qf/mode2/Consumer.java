package com.qf.mode2;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author ken
 * @Date 2019/1/18
 * @Version 1.0
 */
public class Consumer {

    public static void main(String[] args) throws IOException {
        //
        Connection conn = ConnectionUtil.getConnection();

        //通过连接获得管道
        Channel channel = conn.createChannel();

        //基于管道创建队列
        //声明了一个队列，这个队列的名字为simple_queue
        channel.queueDeclare("simple_queue2", false, false, false, null);

        //监听队列
        channel.basicConsume("simple_queue2", true, new DefaultConsumer(channel){

            //消息消费的方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者接收到消息：" + new String(body, "UTF-8"));
            }
        });

    }
}
