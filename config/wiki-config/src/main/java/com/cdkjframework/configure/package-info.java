/**
 * <p>Spring Cloud Config 客户端配置读取及刷新接口</p>
 *
 * 需要在配置文件中配置：
 * management:
 *   endpoints:
 *     web:
 *       exposure:
 *         include: refresh,bus-refresh  # 暴露 /actuator/refresh 若项目整体在内网可考虑配置为
 *         若要自动批量刷新（通过 RabbitMQ 或 Kafka 传递消息） 暴露 Bus-Refresh 端点，还需要引入
 * RabbitMQ：
 * <dependency>
 *     <groupId>org.springframework.cloud</groupId>
 *     <artifactId>spring-cloud-starter-bus-amqp</artifactId>
 * </dependency>
 * Kafka：
 * <dependency>
 *     <groupId>org.springframework.cloud</groupId>
 *     <artifactId>spring-cloud-starter-bus-kafka</artifactId>
 * </dependency>
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.configure
 * @ClassName: package-info
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
package com.cdkjframework.configure;