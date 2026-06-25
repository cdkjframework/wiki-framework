<p align="center">
  <a href="https://framewiki.com">
    <img alt="Wiki-Framework" src="assets/wiki.png" width="400"/>
  </a>
</p>

<p align="center">
  <a href="https://framewiki.com">https://framewiki.com/</a>
</p>

<p align="center">
  <a target="_blank" href="https://search.maven.org/artifact/com.framewiki/wiki-all">
    <img src="https://img.shields.io/maven-central/v/com.framewiki/wiki-all.svg?label=Maven%20Central" alt="Maven Central"/>
  </a>
  <a target="_blank" href="./LICENSE">
    <img src="https://img.shields.io/badge/license-Apache%202.0-blue.svg" alt="Apache License 2.0"/>
  </a>
  <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk17-0-13-later-archive-downloads.html">
    <img src="https://img.shields.io/badge/JDK-17+-red.svg" alt="JDK 17+"/>
  </a>
  <a href="https://gitee.com/cdkjframework/wiki-framework/stargazers">
    <img src="https://gitee.com/cdkjframework/wiki-framework/badge/star.svg?theme=dark" alt="star"/>
  </a>
  <a href="https://gitee.com/cdkjframework/wiki-framework/members">
    <img src="https://gitee.com/cdkjframework/wiki-framework/badge/fork.svg?theme=dark" alt="fork"/>
  </a>
</p>

## 项目介绍

Wiki-Framework 是一个面向 Java 后端开发的模块化基础框架与工具库，基于 JDK 17、Spring Boot 3.3.x 和 Spring Cloud 2023.0.x 构建。项目将日常业务开发中常用的通用能力封装为可独立引用的 Maven 模块，覆盖工具类、统一响应、异常处理、数据源、缓存、消息队列、Socket、对象存储、权限、安全、接口文档、代码生成等场景。

项目目标是减少重复基础代码，让开发者可以更快搭建企业级应用的基础能力，同时保留清晰的源码结构和中文注释，方便二次开发、学习和按需裁剪。

## 项目亮点

- **模块化设计**：按能力拆分为 `wiki-util`、`wiki-core`、`wiki-datasource`、`wiki-redis`、`wiki-security`、`wiki-minio`、`wiki-swagger` 等模块，可单独引入，也可通过 `wiki-all` 聚合引入。
- **适配新版本技术栈**：支持 JDK 17+，基于 Spring Boot 3.3.x、Spring Cloud 2023.0.x，适合新项目基础设施建设。
- **常用能力封装完整**：提供字符串、日期、集合、文件、IO、加解密、JSON、HTTP、JWT、Excel、二维码、邮件、推送、日志、分页、数据库访问等常用能力。
- **企业应用基础组件**：提供多数据源、读写分离、Redis、Kafka、MQTT、RocketMQ、Socket、WebSocket、SSE、MinIO、Spring Security、OAuth2、配置中心等组件封装。
- **代码生成与接口文档**：提供数据库反向生成代码能力，并集成 OpenAPI/Swagger 相关文档能力。
- **开源友好**：使用 Apache License 2.0 开源协议，欢迎 issue、PR 和实际使用反馈。

## 适用场景

- Spring Boot / Spring Cloud 项目的基础框架搭建。
- 中后台、管理系统、微服务项目的公共能力沉淀。
- 需要统一封装工具类、返回结果、异常处理、日志、安全认证、数据源、缓存、消息等能力的 Java 项目。
- 学习企业级 Java 基础组件拆分、封装和 Maven 多模块项目组织方式。

## 技术栈

| 类别 | 技术 |
| --- | --- |
| 运行环境 | JDK 17+ |
| 基础框架 | Spring Boot 3.3.x、Spring Cloud 2023.0.x |
| 数据访问 | MyBatis、Spring Data JPA、MongoDB、PageHelper、Druid |
| 缓存 | Redis |
| 消息队列 | Kafka、MQTT、RocketMQ |
| 通信 | Socket、WebSocket、SSE |
| 对象存储 | MinIO、阿里云 OSS |
| 安全认证 | Spring Security、OAuth2、JWT |
| 文档 | OpenAPI / Swagger / Knife4j |
| 构建 | Maven |

## 模块说明

| 模块 | 说明 |
| --- | --- |
| `wiki-pom` | 统一管理项目依赖版本，作为 BOM/父 POM 使用。 |
| `wiki-all` | 常用模块聚合包，适合快速接入 Wiki-Framework 的主要能力。 |
| `wiki-all-jpa` | 面向 JPA 场景的聚合包。 |
| `wiki-core` | 核心启动配置、统一响应、请求/响应处理、全局异常等基础能力。 |
| `wiki-util` | 字符串、日期、集合、文件、IO、加解密、HTTP、JSON、JWT、Excel、邮件、推送等工具封装。 |
| `wiki-constant` | 公共常量、枚举、缓存 Key、错误码、返回码等定义。 |
| `wiki-entity` | 基础实体、分页参数、分页结果、统一返回结果等通用对象。 |
| `datasource/wiki-datasource` | MyBatis 数据源、分页插件、数据库操作相关封装。 |
| `datasource/wiki-datasource-jpa` | JPA 数据源与 Repository 相关封装。 |
| `datasource/wiki-datasource-mongodb` | MongoDB 数据访问相关封装。 |
| `datasource/wiki-datasource-rw` | MyBatis 读写分离场景封装。 |
| `cache/wiki-redis` | Redis 连接、缓存、分布式锁、发布订阅等封装。 |
| `queue/wiki-kafka` | Kafka 生产端工具封装。 |
| `queue/wiki-kafka-client` | Kafka 消费端工具封装。 |
| `queue/wiki-mqtt` | MQTT 生产端工具封装。 |
| `queue/wiki-mqtt-client` | MQTT 消费端工具封装。 |
| `queue/wiki-rocket` | RocketMQ 生产端工具封装。 |
| `queue/wiki-rocket-client` | RocketMQ 消费端工具封装。 |
| `socket/wiki-socket` | Socket 服务端封装。 |
| `socket/wiki-socket-client` | Socket 客户端封装。 |
| `socket/wiki-sse` | SSE 服务端封装。 |
| `socket/wiki-web-socket` | WebSocket 服务端封装。 |
| `socket/wiki-web-socket-client` | WebSocket 客户端封装。 |
| `wiki-log` | 基于 AOP 的日志记录、参数注入等能力。 |
| `wiki-minio` | MinIO 文件上传、下载、删除、存在性判断、列表等封装。 |
| `security/wiki-security` | Spring Security 认证、授权、验证码、Token、扫码登录等封装。 |
| `security/wiki-oauth2` | OAuth2 相关能力封装。 |
| `config/wiki-config` | Spring Cloud Config 配置读取能力。 |
| `config/wiki-nacos-config` | Nacos 配置读取能力。 |
| `config/wiki-nacos-cloud-config` | Nacos 与 Spring Cloud 配置相关封装。 |
| `wiki-message` | 阿里云短信等消息能力封装。 |
| `wiki-cloud` | 服务发现、调用、配置、熔断、限流、降级等 Spring Cloud 常用能力封装。 |
| `wiki-center` | 根据 MySQL、PostgreSQL 等数据库生成实体、Controller、Service、Mapper、Repository 等代码。 |
| `licenses/wiki-license` | License 生成与验证工具封装。 |
| `licenses/wiki-license-core` | License 核心能力。 |
| `licenses/wiki-license-verify` | License 验证能力。 |
| `wiki-swagger` | OpenAPI/Swagger 文档生成相关封装。 |
| `wiki-ai` | AI 相关能力扩展模块。 |

## 快速开始

### 环境要求

- JDK 17 或更高版本
- Maven 3.8 或更高版本

> 从 `1.0.8` 开始，Wiki-Framework 仅支持 JDK 17+。如果你的项目仍使用 JDK 8，请使用 `1.0.7` 及以下版本，旧版本不再更新。

### Maven 引入

如果希望一次性引入常用模块，可使用 `wiki-all`：

```xml
<dependency>
  <groupId>com.framewiki</groupId>
  <artifactId>wiki-all</artifactId>
  <version>1.2.0</version>
</dependency>
```

如果希望统一管理 Wiki-Framework 相关依赖版本，可在项目中引入 `wiki-pom`：

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.framewiki</groupId>
      <artifactId>wiki-pom</artifactId>
      <version>1.2.0</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

随后按需引入具体模块：

```xml
<dependency>
  <groupId>com.framewiki</groupId>
  <artifactId>wiki-util</artifactId>
</dependency>
```

### Gradle 引入

```groovy
implementation 'com.framewiki:wiki-all:1.2.0'
```

### 启用框架能力

在 Spring Boot 启动类上添加 `@EnableAutoWiki`：

```java
import com.cdkjframework.all.annotation.EnableAutoWiki;
import com.cdkjframework.core.spring.CdkjApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoWiki
@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    CdkjApplication.run(DemoApplication.class, args);
  }
}
```

## 源码构建

克隆项目后，可通过项目根目录下的脚本完成安装、打包和文档生成。

Linux / macOS：

```sh
./wiki.sh install
./wiki.sh doc
./wiki.sh pack
```

Windows PowerShell：

```powershell
.\wiki.ps1 install
.\wiki.ps1 doc
.\wiki.ps1 pack
```

脚本命令说明：

| 命令 | 说明 |
| --- | --- |
| `install` | 执行 Maven 安装，将项目构建并安装到本地 Maven 仓库。 |
| `doc` | 生成聚合 Java API 文档，默认输出到 `target/site/apidocs`。 |
| `pack` | 执行 Maven 打包，生成各模块 jar 包。 |

也可以使用 Maven 直接构建安装：

```sh
mvn clean install -DskipTests
```

构建完成后，即可在本地 Maven 仓库中引用对应模块。

## 文档与链接

- 官方文档：[https://framewiki.com/wiki-framework](https://framewiki.com/wiki-framework)
- API 文档：[https://framewiki.com/wiki-framework/apidocs/](https://framewiki.com/wiki-framework/apidocs/)
- Maven Central：[https://repo1.maven.org/maven2/com/framewiki/wiki-all/](https://repo1.maven.org/maven2/com/framewiki/wiki-all/)
- Gitee 仓库：[https://gitee.com/cdkjframework/wiki-framework](https://gitee.com/cdkjframework/wiki-framework)
- GitHub 仓库：[https://github.com/cdkjframework/wiki-framework](https://github.com/cdkjframework/wiki-framework)
- 示例项目：[https://gitee.com/cdkjframework/framewiki-example](https://gitee.com/cdkjframework/framewiki-example)
- English README：[README.en.md](./README.en.md)

## 分支说明

| 分支 | 说明 |
| --- | --- |
| `master` | 稳定发布分支，与 Maven Central 发布版本保持一致。 |
| `dev` | 开发分支，用于接收新功能、问题修复和 Pull Request。 |

## 参与贡献

欢迎通过 issue 或 Pull Request 参与项目建设。为了便于维护，请尽量遵循以下约定：

1. 提交 issue 时请说明 JDK 版本、Wiki-Framework 版本、相关依赖版本、复现步骤和期望结果。
2. 新增公共方法时请补充必要的 JavaDoc，说明用途、参数、返回值和异常情况。
3. 修改公共模块时建议补充或更新测试用例，避免影响已有使用方。
4. 代码格式遵循 IntelliJ IDEA 默认 Java 格式。
5. Pull Request 请提交到 `dev` 分支，并在描述中说明修改目的、影响范围和验证方式。

## 反馈交流

- Gitee issue：[https://gitee.com/cdkjframework/wiki-framework/issues](https://gitee.com/cdkjframework/wiki-framework/issues)
- GitHub issue：[https://github.com/cdkjframework/wiki-framework/issues](https://github.com/cdkjframework/wiki-framework/issues)
- 邮箱：[wiki@framewiki.com](mailto:wiki@framewiki.com)
- QQ 群：25056933
- 公众号：维基框架（framewiki-com）

## 使用案例

以下公司或团队曾登记使用 Wiki-Framework，欢迎通过 issue 补充更多实际使用案例：

1. 宏图物流股份有限公司
2. 成都乐享智家科技责任有限公司
3. 成都领数云科技有限公司
4. 成都千街万巷商务服务有限公司
5. 成都蓝眸智能科技有限责任公司

## 开源协议

本项目基于 [MulanPSL-2.0](./LICENSE) 开源。
