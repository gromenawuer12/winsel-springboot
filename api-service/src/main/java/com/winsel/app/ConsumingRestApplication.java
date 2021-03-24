package com.winsel.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.winsel.controller")
@ComponentScan(basePackages = "com.winsel.dto")
@EnableEurekaServer
@EnableEurekaClient
public class ConsumingRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumingRestApplication.class, args);
    }

}

