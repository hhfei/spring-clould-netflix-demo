>版本说明
* 官网文档 https://docs.spring.io/spring-cloud-netflix/docs/2.2.8.RELEASE/reference/html/
* eureka 2.2.8.RELEASE 
* spring boot 2.3.2.RELEASE

## 1. 负载均衡Ribbon

### 1. 注册RestTemplate

```java
@Bean
    @LoadBalanced
    public RestTemplate  restTemplate(){
        return new RestTemplate();
    }
```
### 2. 测试消费者，负载均衡方式调用服务，默认值是轮询策略

http://localhost:8090/hello

### 3. 随机的负载均衡

```java
  @Bean
    public IRule iRule(){
        return  new RandomRule();
    }

```

## 2. 服务熔断Hystrix

### 1. pom添加
```xml
<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-hystrix</artifactId>
            <version>1.4.4.RELEASE</version>
        </dependency>
```

### 2. 添加注解
>启动类添加注解@EnableCircuitBreaker

>Controller添加
```java
 @HystrixCommand(fallbackMethod = "error", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })
````

## 3. 声明式消费Feign

### 添加POM依赖
```xml
<dependency>
 <groupId>org.springframework.cloud</groupId>
 <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency> 
<dependency>
 <groupId>org.springframework.cloud</groupId>
 <artifactId>spring-cloud-starter-feign</artifactId>
 <version>1.4.5.RELEASE</version>
</dependency>
```

### 添加注解

在项目入口类上添加@EnableFeignClients 注解表示开启 Spring Cloud Feign
的支持功能；

### 声明服务
定义一个 接口，通过@FeignClient 注解来指定服务名称，绑定服务

```java
@FeignClient("01-springcloud-service-provider")
public interface HelloService {
 @RequestMapping("/service/hello")
 public String hello();
}
```

### 开启服务熔断

>修改yml
```yaml
feign:
  hystrix:
    enabled: false
```

>修改注解，编写实现类
```java
@FeignClient(name = "EUREKA-PROVIDER-01" ,fallback = HystrixClientFallback.class)
public interface HelloService {
 @RequestMapping("/feign/hello")
 public String hello();
}

@Component
public class HystrixClientFallback implements HelloService {

    @Override
    public String hello() {
        return "hystix-hello";
    }
}
```

### 服务熔断，获取异常信息

>使用fallbackFactory
```java
@FeignClient(contextId = "FeignClient-2", name = "EUREKA-PROVIDER-01", fallbackFactory = MyFallbackFactory.class)
```

>定义fallback实现类
```java

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


```


## 4.网关Zuul

>添加pom依赖
```xml
<!--添加 spring cloud 的 zuul 的起步依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>
        <!--添加 spring cloud 的 eureka 的客户端依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```

>修改yml
```yaml
server:
  port: 9090
spring:
  application:
    name: GATEWAY-ZUUL-01
eureka:
  client:
    serviceUrl:
      defaultZone: http://eureka8762:8762/eureka/,http://eureka8761:8761/eureka/


zuul:
  routes:
    hello:
      path: /001/**
      serviceId: eureka-provider-01
    user:
      path: /002/**
      serviceId: eureka-provider-01
```
 
>启动类添加注解
```java
@EnableZuulProxy
```
