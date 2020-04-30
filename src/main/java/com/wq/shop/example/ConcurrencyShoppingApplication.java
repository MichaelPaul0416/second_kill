package com.wq.shop.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.wq.shop.example"})
public class ConcurrencyShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConcurrencyShoppingApplication.class, args);
    }

}
