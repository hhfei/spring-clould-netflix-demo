package com.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer01Application8761 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer01Application8761.class, args);
    }

}
