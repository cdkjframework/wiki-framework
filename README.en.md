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

## Introduction

Wiki-Framework is built for developers. It is a feature-rich and easy-to-use Java toolkit designed to help developers
quickly build projects by providing convenient utilities. These encapsulated utilities cover operations such as string
handling, numbers, collections, encoding, dates, files, I/O, encryption, database JDBC, JSON, HTTP, TCP clients (
WebSocket, Socket), and more, meeting various development needs in different scenarios.

### Wiki Philosophy

`Wiki-Framework` serves both as a toolkit and a knowledge base. We never claim all code to be original; most utility
classes are **curated and adapted** from existing sources. Therefore:

- You can introduce it as a dependency, or directly **copy and modify** the code **without requiring any attribution**.
- We strive to provide **detailed Chinese comments** in the source code to create good learning conditions, making it
  accessible to everyone.
- We welcome **open-source contributions**. If you find **Wiki-Framework** useful, please give us a **star**! Thank you!

---

## Software Architecture

Based on Spring Boot 3.3.5 and Spring Cloud 4.1.4, with integrations including MyBatis, JPA, PageHelper (pagination
plugin), and Alibaba database drivers.

Includes the following tools: JWT, Redis, easyPoi (Excel import/export), MongoDB (Spring Boot-based), WebSocket (
Netty-based), Chinese-to-Pinyin conversion, Swagger2 (API management), Alibaba Cloud OSS, Alibaba Cloud SMS,
fasterxml.jackson, ZXing, MQTTv3, Kryo, CXF Web Services, and encryption/decryption utilities, GZIP compression, HTTP
clients, etc.

## Included Components

A foundational Java utility library that encapsulates file, stream, encryption/decryption, encoding conversion, regex,
threading, XML, and other JDK methods into various Utils classes. Also provides the following components:

| Module                  | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
|-------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| wiki-pom                | Core packaging module controlling dependency versions across all project components.                                                                                                                                                                                                                                                                                                                                                                                      |
| wiki-all                | Core Starter Module,The core starter module provides essential packages required for project startup. To integrate it:​Dependency Import: Include the wiki-all module in your project.​Annotation Activation: Add @EnableAutoWiki to your application's main startup class.|
| wiki-core               | Core utilities module providing essential project startup configurations, common request/response interceptors (with AES encryption/decryption), global exception handling.                                                                                                                                                                                                                                                                                               |
| wiki-util               | Utility library providing HTTP/HTTPS client (HttpUrlConnection-based), script execution (e.g., JavaScript), JSON implementation (Alibaba FastJSON), Excel utilities (EasyExcel), JWT implementation, exception utilities, file I/O, encryption (MD5, Base64, AES, DES, RSA, Unicode, SM), log output (Log4j-based), app push notifications (Uni, Tencent, JPush), email, common regex, hex conversion, object copying, time utilities, deserialization, string utilities. |
| wiki-constant           | Constants module providing common enums and constants (e.g., caching, logging, error codes, cache keys, regex patterns).                                                                                                                                                                                                                                                                                                                                                  |
| wiki-entity             | Entities module providing base classes (BaseVo, BaseDto, BaseEntity), pagination parameters/results, result wrappers, error codes.                                                                                                                                                                                                                                                                                                                                        |
| wiki-datasource         | Database utilities (MyBatis) module: connection pooling, data sources, pagination plugin, DB operation utilities.                                                                                                                                                                                                                                                                                                                                                         |
| wiki-datasource-jpa     | Database utilities (JPA) module: connection pooling, data sources, pagination plugin, DB operation utilities, Repository interfaces.                                                                                                                                                                                                                                                                                                                                      |
| wiki-datasource-mongodb | Database utilities (MongoDB) module: connection pooling, data sources, pagination plugin, DB operation utilities, Repository interfaces.                                                                                                                                                                                                                                                                                                                                  |
| wiki-datasource-rw      | Database utilities (MyBatis) module focused on **read-write separation**.                                                                                                                                                                                                                                                                                                                                                                                                 |
| wiki-redis              | Redis utilities module: connection pooling, Redis operations (caching, distributed locks, pub/sub, key expiration subscriptions).                                                                                                                                                                                                                                                                                                                                         |
| wiki-kafka              | Kafka producer module: connection pooling, message sending utilities.                                                                                                                                                                                                                                                                                                                                                                                                     |
| wiki-kafka-client       | Kafka consumer module: connection pooling, message listening utilities.                                                                                                                                                                                                                                                                                                                                                                                                   |
| wiki-mqtt               | MQTT producer module: connection pooling, message sending utilities.                                                                                                                                                                                                                                                                                                                                                                                                      |
| wiki-mqtt-client        | MQTT consumer module: connection pooling, message listening utilities.                                                                                                                                                                                                                                                                                                                                                                                                    |
| wiki-socket             | Socket server module: connection pooling, message handling utilities (send/receive).                                                                                                                                                                                                                                                                                                                                                                                      |
| wiki-socket-client      | Socket client module: connection pooling, message handling utilities (send/receive).                                                                                                                                                                                                                                                                                                                                                                                      |
| wiki-sse                | SSE server module: connection pooling, message handling utilities (send/receive).                                                                                                                                                                                                                                                                                                                                                                                         |
| wiki-web-socket         | WebSocket server module: connection pooling, message handling utilities (send/receive).                                                                                                                                                                                                                                                                                                                                                                                   |
| wiki-web-socket-client  | WebSocket client module: connection pooling, message handling utilities (send/receive).                                                                                                                                                                                                                                                                                                                                                                                   |
| wiki-log                | Logging module: AOP-based logging for controllers, mappers, repositories with configurable parameter injection.                                                                                                                                                                                                                                                                                                                                                           |
| wiki-minio              | MinIO utilities module: connection pooling, file operations (upload/download/delete/exists/list).                                                                                                                                                                                                                                                                                                                                                                         |
| wiki-security           | Spring Security module: authentication, authorization, permissions, logout, captcha, token refresh, QR code login.                                                                                                                                                                                                                                                                                                                                                        |
| wiki-config             | Spring Cloud Config client module: automatic configuration loading.                                                                                                                                                                                                                                                                                                                                                                                                       |
| wiki-message            | SMS module: Alibaba Cloud SMS utilities (note: voice calls deprecated).                                                                                                                                                                                                                                                                                                                                                                                                   |
| wiki-cloud              | Spring Cloud utilities module: service discovery, invocation, configuration, circuit breaking, rate limiting, fallback.                                                                                                                                                                                                                                                                                                                                                   |
| wiki-center             | Project center module: code generation from databases (MySQL/PostgreSQL) for entities (Vo/Dto/Entity), controllers, services, repositories (JPA), mappers, XML.                                                                                                                                                                                                                                                                                                           |
| wiki-license            | License management utilities: generation and validation.                                                                                                                                                                                                                                                                                                                                                                                                                  |
| wiki-license-core       | License core module.                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| wiki-license-verify     | License verification module.                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| wiki-swagger            | Swagger module: OpenAPI documentation generation.                                                                                                                                                                                                                                                                                                                                                                                                                         |
| wiki-web                | Functional testing module.                                                                                                                                                                                                                                                                                                                                                                                                                                                |

You can selectively include modules as needed (recommended to use `wiki-pom` for dependency version management).
Alternatively, include all modules via `wiki-all`.

## Installation Documentation

[English Documentation](./README.en.md)  
[中文文档](./README.md)  
[备用中文文档](https://framewiki.com/wiki-framework)  
[API Reference](https://framewiki.com/wiki-framework/apidocs/)

## Installation Guide

No installation required. Use directly via dependency management.

#### Usage Instructions

1. Deploy to a Maven repository
2. Add dependency

###### Maven

Add to your top-level POM:

```xml

<parent>
  <groupId>com.framewiki</groupId>
  <artifactId>wiki-pom</artifactId>
  <version>1.1.1</version>
</parent>
```

###### Gradle

```groovy
implementation 'com.framewiki:wiki-pom:1.1.0'
```

#### Download JAR

Download [wiki-all-X.X.X.jar](https://gitee.com/cdkjframework/wiki-framework/releases) directly:

- [Maven Central](https://repo1.maven.org/maven2/com/framewiki/wiki-all/1.1.0/)

> **Note**  
> Since version 1.0.8, Wiki-Framework requires **JDK 17+**. Compatibility with Android is untested.  
> For JDK 8 projects, use Wiki-Framework 1.0.7 or earlier (no longer updated).

#### Build from Source

Visit the Gitee
homepage: [https://gitee.com/cdkjframework/wiki-framework](https://gitee.com/cdkjframework/wiki-framework).  
Download the source (master or 1.1.0 branch), navigate to the project directory, and execute:

```sh
./wiki.sh install
```

You can then include it via Maven.

---

## Contribute

### Branch Structure

The source code of Wiki-Framework is divided into two branches, with the following functionalities:

| Branch | Purpose                                                                        |
|--------|--------------------------------------------------------------------------------|
| master | Release branch (matches central repository). **No PRs accepted**.              |
| dev    | Development branch (next SNAPSHOT version). **Accepts PRs and modifications**. |

### Reporting Bugs or Suggestions

Include JDK version, Wiki-Framework version, and relevant dependencies when reporting issues.

- QQ ①Group： 25056933
- Official Account：维基框架 （framewiki-com）
- E-mail：[wiki@framewiki.com](mailto:wiki@framewiki.com)
- [Gitee Issues](https://gitee.com/cdkjframework/wiki-framework/issues)
- [GitHub Issues](https://github.com/cdkjframework/wiki-framework/issues)

### Contribution Steps

1. Fork the project on Gitee/GitHub.
2. Clone your forked repository locally.
3. Make changes on the **`dev` branch**.
4. Commit and push to your fork.
5. Submit a Pull Request to the original `dev` branch.
6. Await review/merge.

### PR Principles

Wiki-Framework welcomes contributions from anyone who wishes to contribute code. However, the maintainer is particularly
meticulous about details. To accommodate this preference, submitted pull requests (PRs) must adhere to the following
specifications:

1. **Complete Documentation**: Javadoc for new methods (description, params, return).
2. **Adhere to Indentation**: Follow IDEA default style.
3. **Minimize Dependencies**: Avoid third-party libs in core/utils (except `extra` module).
4. **Target `dev` Branch**: `master` is for releases only.
5. **Reopen if Needed**: Closed issues/PRs can be revisited if discussion continues.

#### Contributors

卢布白菜 (Lubai Baicai)

#### Adopting Companies

*(Listed in registration order)*

1. Hongtu Logistics Co., Ltd.
2. Chengdu Lexiang Zhijia Technology Co., Ltd.
3. Chengdu Lingshu Cloud Technology Co., Ltd.
4. Chengdu Qianjie Wanxiang Business Service Co., Ltd.
5. Chengdu Lanmou Intelligent Technology Co., Ltd.
