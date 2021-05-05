package com.master.fallback;

import com.master.service.HelloService;
import org.springframework.stereotype.Component;

@Component
public class HystrixClientFallback implements HelloService {


    @Override
    public String hello() {
        return "in feign HystrixClientFallback.hello";
    }

    @Override
    public String hystrixHello() {
        return "in feign HystrixClientFallback.hystrixHello";
    }
}