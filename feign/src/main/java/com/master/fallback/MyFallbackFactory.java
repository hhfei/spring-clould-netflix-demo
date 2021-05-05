package com.master.fallback;

import com.master.service.HelloService;
import com.master.service.UserService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class MyFallbackFactory implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable throwable) {
        System.out.println("MyFallbackFactory.create");
        System.out.println(throwable);
        return new UserService() {
            @Override
            public String hystrixHello() {
                return throwable.getMessage();
            }


        };
    }
}