package com.master.service;

import com.master.fallback.HystrixClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(contextId = "FeignClient-1", name = "EUREKA-PROVIDER-01" ,fallback = HystrixClientFallback.class)
public interface HelloService {
 @RequestMapping("/feign/hello")
 public String hello();

 @RequestMapping("/hystrix")
 public String hystrixHello();
}