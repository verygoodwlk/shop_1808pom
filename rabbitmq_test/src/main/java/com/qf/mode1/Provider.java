package com.qf.mode1;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author ken
 * @Date 2019/1/18
 * @Version 1.0
 */
public class Provider {

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection conn = ConnectionUtil.getConnection();

        //获取管道 - 后续的所有操作（队列、交换机、发送消息、监听消息...）都是基于管道实现的
        Channel channel = conn.createChannel();

        //基于管道创建队列
        //声明了一个队列，这个队列的名字为simple_queue
        channel.queueDeclare("simple_queue", false, false, false, null);

        //发布消息
        String str = "Hello RabbitMQ!!!!!";
        channel.basicPublish("", "simple_queue", null, str.getBytes("UTF-8"));

        //关闭连接
        conn.close();
    }
}
