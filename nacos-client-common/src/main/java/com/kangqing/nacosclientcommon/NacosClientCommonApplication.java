package com.kangqing.nacosclientcommon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class NacosClientCommonApplication {

    private final LoadBalancerClient loadBalancerClient;

    // 需要@Autowired注入
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private FeignService feignService;

    public static void main(String[] args) {
        SpringApplication.run(NacosClientCommonApplication.class, args);
    }

    /**
     * 1. 通过spring cloud common中的负载均衡接口选取服务提供节点实现接口调用
     * @return
     */
    @GetMapping("/test")
    public String test() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-server");
        String url = serviceInstance.getUri() + "/hello?name=" + "kangqing";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return "Invoke : " + url + ", return : " + result;
    }

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
     * WebClient是Spring 5中最新引入的，可以将其理解为reactive版的RestTemplate。
     * @return
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }


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
