package com.master.master.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/hello")
    public String getUserName(){
        System.out.println("provider-"+serverPort + "-hello");
        return "provider-"+serverPort + "-hello";
    }


    @RequestMapping("/hystrix")
    public String hystix() {
        System.out.println("provider-" + serverPort + "-hystix");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
        return "provider-" + serverPort + "-hystix";
    }

    @RequestMapping("/feign/hello")
    public String feignHello() {
        return "provider-" + serverPort + "-feign-hello";
    }
}
