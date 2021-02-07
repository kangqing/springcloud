# Nacos作为注册中心和配置中心

## 安装Nacos

见[官网](https://nacos.io/zh-cn/docs/quick-start.html)

安装启动后Nacos访问地址是 `127.0.0.1:8848/nacos/index.html`

登录名和密码默认都是 `nacos`

## 使用Nacos作为注册中心

版本对于自己去了解，我这里用的boot 2.3.2.RELEASE版本，spring cloud alibaba 2.2.5.RELEASE版本

```xml
<!--这里是我引入的依赖-->
<!--注册中心的依赖，服务的注册与发现-->
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
  <version>2.2.5.RELEASE</version>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

接下来在入口类的上面加上注解`@EnableDiscoveryClient`,实现服务的注册与发现。

```java
/**
 * 开启Spring Cloud的服务注册与发现
 */
@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class NacosServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosServerApplication.class, args);
    }


    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        return "Hello " + name;
    }

}
```

配置文件`application.yml`中配置注册中心地址和服务注册到注册中心的名称

```yaml
server:
  port: 7001
# 注册到注册中心服务的名称
spring:
  application:
    name: nacos-server
  # nacos 注册中心地址
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

启动后去Nacos注册中心查看，可以看到配置的服务注册到了注册中心

![图1](https://yunqing-img.oss-cn-beijing.aliyuncs.com/hexo/article/202102/image-20210206132200241.png)

## 介绍三种服务的消费方式

这里不介绍RPC调用，分别介绍 spring 提供的 RestTemplate、WebClient 还有 Netflix 封装的 Feign一共三种方式。

### RestTemplate 方式

首先新建一个新的服务用来调用`nacos-server`服务，新建的服务命名为`nacos-client-common`，配置关系和`nacos-server`一样

```java
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class NacosClientCommonApplication {

    // 需要@Autowired注入
    @Autowired
    private RestTemplate restTemplate;
  
  
  /**
     * 2. Spring Cloud中对RestTemplate做了增强，只需要稍加配置，就能简化之前的调用方式
     * @return
     */
    @GetMapping("/test1")
    public String test1() {
        return restTemplate.getForObject("http://nacos-server/hello?name=kangkang", String.class);
    }

    /**
     * 在定义RestTemplate的时候，增加了@LoadBalanced注解，而在真正调用服务接口的时候，原来host部分是通过手工拼接ip和端口的，
     * 直接采用服务名的时候来写请求路径即可。在真正调用的时候，Spring Cloud会将请求拦截下来，然后通过负载均衡器选出节点，
     * 并替换服务名部分为具体的ip和端口，从而实现基于服务名的负载均衡调用。
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```



### WebClient 方式

WebClient 是 Spring5 响应式编程中类似 RestTemplate 调用服务的方式

```xml
# 需要引入依赖支持响应式编程
<!--用于支持WebClient代替RestTemplate调用服务-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

```java
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class NacosClientCommonApplication {
  
  	@Autowired
    private WebClient.Builder webClientBuilder;
  
    /**
     * RestTemplate和 WebClient都是 Spring 自己封装的工具
     * @return
     */
    @GetMapping("/test2")
    public Mono<String> test2() {
        return webClientBuilder.build()
                .get()
                .uri("http://nacos-server/hello?name=yunqing")
                .retrieve()
                .bodyToMono(String.class);
    }


    /**
     * 依赖 spring-boot-starter-webflux
     * WebClient是Spring 5中最新引入的，可以将其理解为响应式版的RestTemplate。
     * @return
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}
```

### Feign 方式

```xml
<!--引入依赖-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-openfeign</artifactId>
  <version>2.2.6.RELEASE</version>
</dependency>
```

```java
// 加入下面这个注解，开始扫描 FeignClient 
@EnableFeignClients
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class NacosClientCommonApplication {
  	// 注入服务的接口，需要把服务的接口声明在这个服务的项目中，实现还是在 nacos-server 中
  	@Autowired
    private FeignService feignService;
  
  	/**
     * 依赖 spring-cloud-starter-openfeign
     *
     * @return
     */
    @GetMapping("/test3")
    public String test3() {
        return feignService.hello("spring cloud");
    }
}
```

服务的接口 FeignServer

```java
// 注解指定了这个接口调用服务的名称
@FeignClient("nacos-server")
public interface FeignService {

    @GetMapping("/hello")
    String hello(@RequestParam String name);
}
```



