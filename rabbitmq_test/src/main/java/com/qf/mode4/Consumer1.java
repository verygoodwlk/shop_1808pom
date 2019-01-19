package com.qf.mode4;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author ken
 * @Date 2019/1/18
 * @Version 1.0
 */
public class Consumer1 {

    public static void main(String[] args) throws IOException {
        Connection conn = ConnectionUtil.getConnection();
        Channel channel = conn.createChannel();

        channel.basicConsume("queue1", true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1消费了：" + new String(body));
            }
        });

    }
}
