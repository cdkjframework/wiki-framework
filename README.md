<p align="center">
	<a href="https://framewiki.com"><img alt="keytool" src="assets/wiki.png"/></a>
</p>

<p align="center">
	<a href="https://framewiki.com">https://framewiki.com/</a>
</p>

<p align="center">
  <a href='https://gitee.com/cdkjframework/wiki-proxy/stargazers'><img src='https://gitee.com/cdkjframework/wiki-proxy/badge/star.svg?theme=dark' alt='star'></img></a>
<a href='https://gitee.com/cdkjframework/wiki-proxy/members'><img src='https://gitee.com/cdkjframework/wiki-proxy/badge/fork.svg?theme=dark' alt='fork'></img></a>
<a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk17-0-13-later-archive-downloads.html">
    <img src="https://img.shields.io/badge/JDK-17+-red.svg" />
</a>
<a href="./LICENSE">
    <img src="https://img.shields.io/badge/license-MIT-red" alt="license MIT">
</a>
</p>

## 介绍

Wiki-Framework 为开发者而生、；是一个功能丰富且易用的Java工具库，通过诸多实用工具类的使用，旨在帮助开发为快速搭建开发项目提供便利。
这些封装的工具涵盖了字符串、数字、集合、编码、日期、文件、IO、加密、数据库JDBC、JSON、HTTP、TCP（WebSocket、Socket）客户端等一系列操作，可以满足各种不同场景的开发需求。

## 软件架构

基于Spring Boot 3.3.5 及 Spring Cloud 4.1.4，MyBatis、JPA、PageHelper（分页插件）、Alibaba数据库驱动

包含以下工具：JWT、Redis、easyPoi导入导出excel、mongodb（基于Spring Boot）、webSocket（基于netty）、汉字转拼音、swagger2（接口管理）、AliYun
OSS、AliYun 短信、fasterxml.jackson、zxing、mqttv3、kryo、cxf Web Service及密码加密码（或解密）、GZIP压缩、HTTP请求等

## 包含组件

一个Java基础工具类，对文件、流、加密解密、转码、正则、线程、XML等JDK方法进行封装，组成各种Utils工具类，同时提供以下组件：

| 模块        | 介绍                                                     |
|-----------|--------------------------------------------------------|
| wiki-core | 核心工具模块，提供项目工程启动的必要配置，公共请求响应拦截器（实现参数AES加觖密处理），公共异常捕获，处理 |

## 安装教程

不需要安装直接使用

#### 使用说明

1. deploy 上传至 maven 仓
2. 使用引用

在项目最外层POM文件引入：

    <parent>
        <groupId>com.framewiki</groupId>
        <artifactId>wiki-pom</artifactId>
        <version>1.0.11</version>
    </parent>

#### 参与贡献

卢布白菜

#### 使用公司

注：排名根据登记序列

1、宏图物流股份有限公司

2、成都乐享智家科技责任有限公司

3、成都领数云科技有限公司

4、成都千街万巷商务服务有限公司

5、成都蓝眸智能科技有限责任公司