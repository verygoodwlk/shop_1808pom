package com.qf.shop_item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author ken
 * @Date 2019/1/18
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.qf")
public class ShopItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopItemApplication.class, args);
    }
}
