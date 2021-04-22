package com.winsel.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.winsel.controller")
@ComponentScan(basePackages = "com.winsel.service")
@ComponentScan(basePackages = "com.winsel.dto")
@EnableJpaRepositories("com.winsel.dao")
@EntityScan("com.winsel.dao.entity")
@EnableEurekaClient
@EnableFeignClients("com.winsel.feign")
public class SpringApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }

}
