package com.winsel.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.winsel.controller")
@ComponentScan(basePackages = "com.winsel.dto")
public class ConsumingRestApplication {

    private static final Logger log = LoggerFactory.getLogger(ConsumingRestApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ConsumingRestApplication.class, args);
    }

}

