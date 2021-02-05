package com.kangqing.nacosclientcommon;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 总结：
 * -------------------------------------------------------------------------
 * 如果之前已经用过Spring Cloud的读者，肯定会这样的感受：不论我用的是RestTemplate也好、还是用的WebClient也好，还是用的Feign也好，
 * 似乎跟我用不用Nacos没啥关系？我们在之前介绍Eureka和Consul的时候，也都是用同样的方法来实现服务调用的，不是吗？
 *
 * 确实是这样，对于Spring Cloud老手来说，就算我们更换了Nacos作为新的服务注册中心，其实对于我们应用层面的代码是没有影响的。那么为什么
 * Spring Cloud可以带给我们这样的完美编码体验呢？实际上，这完全归功于Spring Cloud Common的封装，由于在服务注册与发现、客户端负载均衡
 * 等方面都做了很好的抽象，而上层应用方面依赖的都是这些抽象接口，而非针对某个具体中间件的实现。所以，在Spring Cloud中，我们可以很方便的
 * 去切换服务治理方面的中间件。
 * -------------------------------------------------------------------------
 *
 *
 * 先通过@EnableFeignClients注解开启扫描Spring Cloud Feign客户端的功能
 * 然后又创建一个Feign的客户端接口定义。使用@FeignClient注解来指定这个接口所要调用的服务名称，接口中定义的各个函数使用Spring MVC的注解就可以来绑定服务提供方的REST接口
 * @author kangqing
 * @since 2021/2/5 14:40
 */
@FeignClient("nacos-server")
public interface FeignService {

    @GetMapping("/hello")
    String hello(@RequestParam String name);
}
