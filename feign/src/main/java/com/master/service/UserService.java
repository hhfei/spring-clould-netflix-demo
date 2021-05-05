package com.master.service;

import com.master.fallback.HystrixClientFallback;
import com.master.fallback.MyFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(contextId = "FeignClient-2", name = "EUREKA-PROVIDER-01", fallbackFactory = MyFallbackFactory.class)
public interface UserService {
    @RequestMapping("/hystrix")
    public String hystrixHello();
}