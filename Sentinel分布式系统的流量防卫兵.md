# Sentinel: 分布式系统的流量防卫兵



随着微服务的流行，服务和服务之间的稳定性变得越来越重要。Sentinel 以流量为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性。

Sentinel 具有以下特征:

- **丰富的应用场景**：Sentinel 承接了阿里巴巴近 10 年的双十一大促流量的核心场景，例如秒杀（即突发流量控制在系统容量可以承受的范围）、消息削峰填谷、集群流量控制、实时熔断下游不可用应用等。
- **完备的实时监控**：Sentinel 同时提供实时的监控功能。您可以在控制台中看到接入应用的单台机器秒级数据，甚至 500 台以下规模的集群的汇总运行情况。
- **广泛的开源生态**：Sentinel 提供开箱即用的与其它开源框架/库的整合模块，例如与 Spring Cloud、Dubbo、gRPC 的整合。您只需要引入相应的依赖并进行简单的配置即可快速地接入 Sentinel。
- **完善的 SPI 扩展点**：Sentinel 提供简单易用、完善的 SPI 扩展接口。您可以通过实现扩展接口来快速地定制逻辑。例如定制规则管理、适配动态数据源等。



## 整合到应用中

下载sentinel的仪表板： [下载地址](https://github.com/alibaba/Sentinel/releases)

启动sentinel仪表板，默认8080端口，这里我们在命令中改成7010端口启动

```bash
java -jar sentinel-dashboard-1.8.1.jar --server.port=7010
```

我直接在`nacos-config`服务中整合了，加入依赖

```xml
<!--整合sentinel的依赖-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    <version>2.2.5.RELEASE</version>
</dependency>
```

添加配置sentinel仪表板的地址

```yaml
# 配置sentinel 仪表板的访问地址
spring:
  cloud:
    sentinel:
      transport:
        dashboard: 127.0.0.1:7010
```

## 实现接口限流

启动之后查看仪表板 `127.0.0.1:7010`

![图1](https://yunqing-img.oss-cn-beijing.aliyuncs.com/hexo/article/202102/image-20210207114829266.png)

可以看到仪表板中针对服务`nacos-config`已经进行了管理，下面在簇点链路中的`/test`这个接口的`流控`进行以下流量控制，`qps`的单机阈值设置为 2，即每秒内最多允许两个请求进入。

测试一下，一秒内请求 4 次，得到如下结果：

可以看到前两次正常返回，后两次写道：被 Sentinel 封锁（限流）

![图2](https://yunqing-img.oss-cn-beijing.aliyuncs.com/hexo/article/202102/image-20210207133715975.png)

