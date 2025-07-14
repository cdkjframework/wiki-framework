<p align="center">
	<a href="https://framewiki.com"><img alt="keytool" src="assets/wiki.png"/></a>
</p>

<p align="center">
	<a href="https://framewiki.com">https://framewiki.com/</a>
</p>

<p align="center">
	<a target="_blank" href="https://search.maven.org/artifact/com.framewiki/wiki-all">
		<img src="https://img.shields.io/maven-central/v/com.framewiki/wiki-all.svg?label=Maven%20Central" />
	</a>
	<a target="_blank" href="https://license.coscl.org.cn/MulanPSL2">
		<img src="https://img.shields.io/:license-MulanPSL2-blue.svg" />
	</a>
  <a href='https://gitee.com/cdkjframework/wiki-framework/stargazers'><img src='https://gitee.com/cdkjframework/wiki-proxy/badge/star.svg?theme=dark' alt='star'></img></a>
<a href='https://gitee.com/cdkjframework/wiki-framework/members'><img src='https://gitee.com/cdkjframework/wiki-proxy/badge/fork.svg?theme=dark' alt='fork'></img></a>
<a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk17-0-13-later-archive-downloads.html">
    <img src="https://img.shields.io/badge/JDK-17+-red.svg" />
</a>
<a href="./LICENSE">
    <img src="https://img.shields.io/badge/license-MIT-red" alt="license MIT">
</a>
</p>

## 介绍

Wiki-Framework 为开发者而生；是一个功能丰富且易用的Java工具库，通过诸多实用工具类的使用，旨在帮助开发为快速搭建开发项目提供便利。
这些封装的工具涵盖了字符串、数字、集合、编码、日期、文件、IO、加密、数据库JDBC、JSON、HTTP、TCP（WebSocket、Socket）客户端等一系列操作，可以满足各种不同场景的开发需求。

### Wiki 理念

`Wiki-Framework`既是一个工具集，也是一个知识库，我们从不自诩代码原创，大多数工具类都是**搬运整理**而来，因此：

- 你可以引入使用，也可以**拷贝**和**修改**使用，而**不必标注任何信息**，我们只是希望能及时反馈bug回来。
- 我们努力完善**详细的中文**注释，为源码学习者提供良好地学习条件，争取做到人人都能看得懂。
- 我们也欢迎**开源贡献**，如果你觉得**Wiki-Framework**对您有用，请**star**，谢谢！

-------------------------------------------------------------------------------

## 软件架构

基于Spring Boot 3.3.5 及 Spring Cloud 4.1.4，MyBatis、JPA、PageHelper（分页插件）、Alibaba数据库驱动

包含以下工具：JWT、Redis、easyPoi导入导出excel、mongodb（基于Spring Boot）、webSocket（基于netty）、汉字转拼音、swagger2（接口管理）、AliYun
OSS、AliYun 短信、fasterxml.jackson、zxing、mqttv3、kryo、cxf Web Service及密码加密码（或解密）、GZIP压缩、HTTP请求等

## 包含组件

一个Java基础工具类，对文件、流、加密解密、转码、正则、线程、XML等JDK方法进行封装，组成各种Utils工具类，同时提供以下组件：

| 模块                      | 介绍                                                                                                                                                                                                                                                                                         |
|-------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| wiki-pom                | 核心包装模块，提供项目工程所有包含的依赖包的版本的控制                                                                                                                                                                                                                                                                |
| wiki-all                | 核心启动模块，提供项目工程启动的必要包项目，只需要引入wiki-all模块即可；所在项目在启动类添加注解 @EnableAutoWiki                                                                                                                                                                                                                                     |
| wiki-core               | 核心工具模块，提供项目工程启动的必要配置，公共请求响应拦截器（实现参数AES加觖密处理），公共异常捕获，处理                                                                                                                                                                                                                                     |
| wiki-util               | 工具库提供了基于HttpUrlConnection的Http，Https客户端封装、脚本执行封装，例如Javascript、 JSON实现（基于 alibaba fastjson）、 针对EsayExcel中Excel的封装、JSON Web Token (JWT)封装实现、异常工具封装、文件的读写、MD5，Base64,Aes,Des,Rsa,Unicode,国密等加解密工具、日志输出的封装（基于log4j）、APP消息推送（支持Uni，腾讯，极光）、电子邮件、常用正则、16进制转换、实体拷贝、时间工具、反序列化工具、字符串工具（空，非空判断以及常用字符） |
| wiki-constant           | 常量工具模块，提供项目运行所需的一些常用常量及枚举，例如：缓存、日志、错误码、返回码、缓存的Key、常用正则表达示                                                                                                                                                                                                                                  |
| wiki-entity             | 实体工具模块，提供项目运行所需一些实体类，例如：分页参数，分页结果，返回结果，错误码，返回码以及实体基类（BaseVo, BaseDto, BaseEntity）                                                                                                                                                                                                          |
| wiki-datasource         | 数据库工具模块（MyBatis），提供数据库连接池，数据源，分页插件，数据库操作工具类                                                                                                                                                                                                                                                |
| wiki-datasource-jpa     | 数据库工具模块（JPA），提供数据库连接池，数据源，分页插件，数据库操作工具类，Repository接口                                                                                                                                                                                                                                       |
| wiki-datasource-mongodb | 数据库工具模块（Mongodb），提供数据库连接池，数据源，分页插件，数据库操作工具类，Repository接口                                                                                                                                                                                                                                   |
| wiki-datasource-rw      | 数据库工具模块（MyBatis）【主要用于读写分离】，提供数据库连接池，数据源，分页插件，数据库操作工具类；                                                                                                                                                                                                                                     |
| wiki-redis              | Redis工具模块，提供Redis连接池，Redis操作工具类，例如：缓存，分布式锁，发布订阅，订阅消息（发布的消息订阅、KEY过期订阅）                                                                                                                                                                                                                      |
| wiki-kafka              | Kafka工具模块，提供Kafka连接池，Kafka操作工具类，例如：发送消息                                                                                                                                                                                                                                                    |
| wiki-kafka-client       | Kafka客户端模块，提供Kafka连接池，Kafka操作工具类，例如：监听消息                                                                                                                                                                                                                                                   |
| wiki-mqtt               | Mqtt工具模块，提供Mqtt连接池，Mqtt操作工具类，例如：发送消息                                                                                                                                                                                                                                                       |
| wiki-mqtt-client        | Mqtt客户端模块，提供Mqtt连接池，Mqtt操作工具类，例如：监听消息                                                                                                                                                                                                                                                      |
| wiki-socket             | Socket服务端模块，提供Socket连接池，Socket操作工具类，监听消息接口（需要自行实现），例如：发送消息、接收消息                                                                                                                                                                                                                            |
| wiki-socket-client      | Socket客户端模块，提供Socket连接池，Socket操作工具类，监听消息接口（需要自行实现），例如：发送消息、接收消息                                                                                                                                                                                                                            |
| wiki-sse                | SSE服务端模块，提供SSE连接池，SSE操作工具类，监听消息接口（需要自行实现），例如：发送消息、接收消息                                                                                                                                                                                                                                     | |
| wiki-web-socket         | WebSocket服务端模块，提供WebSocket连接池，WebSocket操作工具类，监听消息接口（需要自行实现），例如：发送消息、接收消息                                                                                                                                                                                                                   |
| wiki-web-socket-client  | WebSocket客户端模块，提供WebSocket连接池，WebSocket操作工具类，监听消息接口（需要自行实现），例如：发送消息、接收消息                                                                                                                                                                                                                   |
| wiki-log                | 日志工具模块，通过AOP切面记录日志（提供controller，mapper，repository切面），例如：记录日志（只限controller层）、参数注入（支持mapper，repository切面）【通过配置参数实现】                                                                                                                                                                          |
| wiki-minio              | MinIO工具模块，提供MinIO连接池，MinIO操作工具类，例如：上传文件、下载文件、删除文件、文件是否存在、文件列表                                                                                                                                                                                                                              |
| wiki-security           | Spring Security工具模块，提供Spring Security连接池，Spring Security操作工具类，例如：用户认证、用户授权、用户权限、用户登出、验证码、刷新token、票据生成、票据验证、二维码扫码登录。                                                                                                                                                                        |
| wiki-config             | Spring Cloud Config配置中心读取模块，提供Spring Cloud Config配置中心读取工具类，例如：自动读取配置文件                                                                                                                                                                                                                     |                    
| wiki-message            | 短信消息模块，提供阿里云短信消息工具类，例如：发送短信、拨打电话（已无法申请）                                                                                                                                                                                                                                                    |                    
| wiki-cloud              | Spring Cloud工具模块，提供例如：服务注册、服务发现、服务调用、服务配置、服务熔断、服务限流、服务降级                                                                                                                                                                                                                                   |
| wiki-center             | 项目中心模块，提供项目通过数据库（MySQL、PostgreSQL）生成代码，例如：生成实体类（Vo，Ddt，Entity）、生成Controller、生成Service、生成ServiceImpl、生成Repository（JPA）、生成Mapper、生成Mapper.xml、                                                                                                                                               |
| wiki-license            | License工具模块，提供License工具类，例如：生成License、验证License                                                                                                                                                                                                                                            |
| wiki-license-core       | License核心模块                                                                                                                                                                                                                                                                                |
| wiki-license-verify     | License验证模块、提供License验证工具类，例如：验证License                                                                                                                                                                                                                                                    |
| wiki-swagger            | Swagger工具模块，提供Swagger连接池，Swagger操作工具类，例如：生成Swagger文档【使用的是openapi】                                                                                                                                                                                                                          |      |
| wiki-web                | 功能测试模块                                                                                                                                                                                                                                                                                     |

可以根据需求对每个模块单独引入(建议引用wiki-pom来控制三方包版本以包证不会冲突)，也可以通过引入wiki-all方式引入所有模块。

## 安装文档

[English Documentation](./README.en.md)

[中文文档](./README.md)

[中文备用文档](https://framewiki.com/wiki-framework)

[参考API](https://framewiki.com/wiki-framework/apidocs/)

## 安装教程

不需要安装直接使用

#### 使用说明

1. deploy 上传至 maven 仓
2. 使用引用

###### Maven

在项目最外层POM文件引入：

    <parent>
        <groupId>com.framewiki</groupId>
        <artifactId>wiki-all</artifactId>
        <version>1.0.12</version>
    </parent>

###### Gradle

    implementation 'com.framewiki:wiki-all:1.0.12'

#### 下载 jar

点击以下链接，下载[wiki-all-X.X.X.jar](https://gitee.com/cdkjframework/wiki-framework/releases)即可

- [Maven中央库](https://repo1.maven.org/maven2/com/framewiki/wiki-all/1.0.12/)

> 注意
> wiki-framework 1.0.8 开始只支持JDK17+，对Android平台没有测试，不能保证所有工具类或工具方法可用。
> 如果你的项目使用JDK8，请使用 wiki-framework 1.0.7及以下版本（不再更新）

#### 编译安装

访问 wiki-framework
的Gitee主页：[https://gitee.com/cdkjframework/wiki-framework](https://gitee.com/cdkjframework/wiki-framework)
下载整个项目源码（master或1.0.12分支都可）然后进入wiki-framework项目目录执行：

```sh
./wiki.sh install
```

然后就可以使用Maven引入了。

## 示例项目

[维基框架示例项目](https://gitee.com/cdkjframework/framewiki-example)

-------------------------------------------------------------------------------

## 添砖加瓦

### 分支说明

Wiki-Framework的源码分为两个分支，功能如下：

| 分支     | 作用                                         |
|--------|--------------------------------------------|
| master | 主分支，release版本使用的分支，与中央库提交的jar一致，不接收任何pr或修改 |
| dev    | 开发分支，默认为下个版本的SNAPSHOT版本，接受修改或pr            |

### 提供bug反馈或建议

提交问题反馈请说明正在使用的JDK版本呢、Wiki-Framework 版本和相关依赖库版本。

- [Gitee issue](https://gitee.com/cdkjframework/wiki-framework/issues)
- [Github issue](https://github.com/cdkjframework/wiki-framework/issues)

### 贡献代码的步骤

1. 在Gitee或者Github上fork项目到自己的repo
2. 把fork过去的项目也就是你的项目clone到你的本地
3. 修改代码（记得一定要修改dev分支）
4. commit后push到自己的库（dev分支）
5. 登录Gitee或Github在你首页可以看到一个 pull request 按钮，点击它，填写一些说明信息，然后提交即可。
6. 等待维护者合并

### PR遵照的原则

Wiki-Framework欢迎任何人为Wiki-Framework添砖加瓦，贡献代码，不过维护者是一个强迫症患者，为了照顾病人，需要提交的pr（pull
request）符合一些规范，规范如下：

1. 注释完备，尤其每个新增的方法应按照Java文档规范标明方法说明、参数说明、返回值说明等信息，必要时请添加单元测试，如果愿意，也可以加上你的大名。
2. Wiki-Framework的缩进按照IDEA默认缩进，所以请遵守。
3. 新加的方法不要使用第三方库的方法，Wiki-Framework遵循无依赖原则（除非在extra模块中加方法工具）。
4. 请pull request到`dev`分支。`master`是主分支，表示已经发布中央库的版本，这个分支不允许pr，也不允许修改。
5. 我们如果关闭了你的issue或pr，请不要诧异，这是我们保持问题处理整洁的一种方式，你依旧可以继续讨论，当有讨论结果时我们会重新打开。

#### 参与贡献

卢布白菜

#### 使用公司

注：排名根据登记序列

1、宏图物流股份有限公司

2、成都乐享智家科技责任有限公司

3、成都领数云科技有限公司

4、成都千街万巷商务服务有限公司

5、成都蓝眸智能科技有限责任公司
