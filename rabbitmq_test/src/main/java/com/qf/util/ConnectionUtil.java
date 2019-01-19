package com.qf.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author ken
 * @Date 2019/1/18
 * @Version 1.0
 */
public class ConnectionUtil {

    private static ConnectionFactory factory;

    static {
        //连接Rabbitmq
        factory = new ConnectionFactory();
        factory.setHost("192.168.226.180");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/admin");
    }

    /**
     * 获得Rabbitmq的连接工具方法
     * @return
     */
    public static Connection getConnection(){

        //通过工厂获取连接
        Connection conn = null;
        try {
            conn = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
