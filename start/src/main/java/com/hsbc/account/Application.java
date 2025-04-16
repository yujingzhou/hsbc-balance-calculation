package com.hsbc.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Starter
 *
 * @author yubo
 */
@SpringBootApplication(scanBasePackages = {"com.hsbc.account", "com.alibaba.cola"})
@MapperScan("com.hsbc.account.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
