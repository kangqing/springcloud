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

## Nacos作为配置中心

### 创建命名空间

首先需要在Nacos中新增两个命名空间，`dev` 和 `test`之后在每个命名空间下添加对应的配置。

![图2](https://yunqing-img.oss-cn-beijing.aliyuncs.com/hexo/article/202102/image-20210207093226135.png)

如图所示还有一个保留空间`public`这是默认存在的,接下来在`配置管理`的`配置列表`中就可以看到三个命名空间，如下图所示，我在`dev`命名空间创建了一个配置。

![图3](https://yunqing-img.oss-cn-beijing.aliyuncs.com/hexo/article/202102/image-20210207093630848.png)

### 如何定位一个配置

1. 首先由`命名空间namespace`定位是哪个命名空间的配置，必须使用命名空间id，不配置默认使用`public`命名空间
2. `前缀prefix`确定了配置的名称，不配置默认为当前服务名称，这里我配置的也是当前服务名称
3. `后缀file-extension`确定了配置的后缀名，默认为`properties`,因为我的配置的`Data Id`创建为`yaml`格式的名为`nacos-config.yaml`所以这里的后缀需要写出为`yaml`
4. `分组group`确定了配置的组名，默认组名为`DEFAULT_GROUP`
5. 也可以通过`spring.profile.active`或者`group`进行多环境配置，但是更推荐使用`namespace`



- bootstrap.yml

```yaml
# 此处配置必须用 bootstrap.yml 或者 bootstrap.properties
spring:
  application:
    name: nacos-config
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
        file-extension: yaml # dataId由应用名称(name)加文件扩展名组成 nacos-config.yaml, 默认值为 properties
        group: DEFAULT_GROUP # 默认分组, 例如 DEV-GROUP  TEST-GROUP
        prefix: ${spring.application.name} # 前缀默认就是应用名称，不用配置，这里配置出来方便理解
        namespace: 87d915fc-e1be-4d0e-8dbd-d3314ac47844  # 命名空间


server:
  port: 7007
```

### 到底配置了什么？

实际上就配置了一个`kangqing.title`

![图4](https://yunqing-img.oss-cn-beijing.aliyuncs.com/hexo/article/202102/image-20210207095209149.png)



### 读取配置详情

读取配置的目的是证明，通过配置中心添加的配置，能够在应用中生效，被读取到即能够生效

```xml
<!-- 这个是配置中心的依赖 -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    <version>2.2.5.RELEASE</version>
</dependency>
```

```java
/**
 * 可以看到，这个例子中并没有加入nacos的服务发现模块，所以这两个内容是完全可以独立使用的
 *
 * 这个注解 @RefreshScope 主要用来让这个类下的配置内容支持动态刷新，也就是当我们的应用启动之后，修
 * 改了Nacos中的配置内容之后，这里也会马上生效。
 */
@RefreshScope
@RestController
@SpringBootApplication
public class NacosConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class, args);
    }

    /**
     * 注意这个冒号不能省略
     */
    @Value("${kangqing.title:}")
    private String title;

    @GetMapping("/test")
    public String hello() {
        return "result -> " + title;
    }

}
```

### 多配置加载

例如我现在已经加载了`nacos-config.yaml`的配置，我不想把所有配置写到这一个文件中，于是我又在nacos中添加了`mybatis-plus.yaml`的配置，想要在应用中加载多个配置

#### 方式一：

```yaml
spring:
  application:
    name: nacos-config
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
        namespace: 535d5ae5-de1e-443b-a0a7-68a34e52946d
        ext_config[0]:
          data-id: nacos-config.yaml
          group: DEFAULT_GROUP
          refresh: true
        ext_config[1]:
          data-id: mybatis-plus.yaml
          group: DEFAULT_GROUP
          refresh: true
```

#### 方式二：

```yaml
spring:
  application:
    name: nacos-config
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
        namespace: 535d5ae5-de1e-443b-a0a7-68a34e52946d
        shared-configs:
          - nacos-config.yaml
          - mybatis-plus.yaml
        refresh-enabled: true
```

既然有多种配置方式，就存在优先级，最先的前缀后缀group定位的方式优先级最高，其次是方式一，最后是方式二，也就是方式二会被方式一覆盖，方式一会被前后缀group的方式覆盖。



- 注：以上知识点均是本人基于单机版的nacos进行学习总结，并不适用于生产环境，生产环境请另行部署nacos集群和nacos数据持久化。