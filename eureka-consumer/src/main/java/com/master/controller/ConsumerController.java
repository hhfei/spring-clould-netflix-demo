package com.master.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController

public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/hello")
    public String hello() {
        return restTemplate.getForEntity("http://EUREKA-PROVIDER-01/hello", String.class).getBody();
    }

    @HystrixCommand(fallbackMethod = "error", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })


    //熔断器，调用不通，回调 error()方法
    @RequestMapping("/hystrix")
    public String helloHystix() {
        return restTemplate.getForEntity("http://EUREKA-PROVIDER-01/hystrix", String.class).getBody();
    }

    public String error(Throwable throwable) {
        System.out.println(throwable);
        return "error";
    }
}
