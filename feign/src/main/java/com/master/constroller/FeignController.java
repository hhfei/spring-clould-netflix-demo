package com.master.constroller;

import com.master.service.HelloService;
import com.master.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {
    @Autowired
    HelloService helloService;
    @Autowired
    UserService userService;

    @RequestMapping("/feign/hello")
    public String hello() {


        return helloService.hello();
    }

    @RequestMapping("/feign/hystrix")
    public String helloHystix() {


        return helloService.hystrixHello();
    }

    @RequestMapping("/feign/hystrix2")
    public String helloHystix2() {
        return userService.hystrixHello();
    }
}