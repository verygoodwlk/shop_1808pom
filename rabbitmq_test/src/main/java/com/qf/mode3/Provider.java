package com.qf.mode3;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @Author ken
 * @Date 2019/1/18
 * @Version 1.0
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        Connection conn = ConnectionUtil.getConnection();
        Channel channel = conn.createChannel();
        //声明交换机 - 没有存储数据的能力
        channel.exchangeDeclare("simple_exchange", "fanout");

        //声明两个队列
        channel.queueDeclare("queue1", false, false, false, null);
        channel.queueDeclare("queue2", false, false, false, null);
        //将队列和交换机进行绑定
        channel.queueBind("queue1", "simple_exchange", "");//队列绑定交换机
        channel.queueBind("queue2", "simple_exchange", "");//队列绑定交换机
//        channel.exchangeBind();//交换机绑定交换机

        //发送消息
        String str = "Hello World！！！！";
        channel.basicPublish("simple_exchange", "", null, str.getBytes("UTF-8"));

        //关闭连接
        conn.close();
    }
}
