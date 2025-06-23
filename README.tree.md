cdkjframework-common
├─LICENSE 
├─pom.xml                        # Maven 构建文件
README.tree.md                   # 项目的说明文档 - 树结构
├─README.en.md                   # 项目的说明文档 - 英文
├─README.md                      # 项目的说明文档
├─socket                         # socket 项目
|   ├─pom.xml
|   ├─wiki-web-socket-client
|   |           ├─pom.xml
|   |           ├─src
|   |           |  ├─main
|   |           |  |  ├─java
|   |           |  |  |  ├─com
|   |           |  |  |  |  ├─cdkjframework
|   |           |  |  |  |  |       ├─web
|   |           |  |  |  |  |       |  ├─socket
|   |           |  |  |  |  |       |  |   ├─client
|   |           |  |  |  |  |       |  |   |   ├─WebSocketClient.java
|   |           |  |  |  |  |       |  |   |   └WebSocketService.java
|   ├─wiki-web-socket
|   |        ├─pom.xml
|   |        ├─src
|   |        |  ├─main
|   |        |  |  ├─resources
|   |        |  |  |     ├─META-INF
|   |        |  |  |     |    ├─spring
|   |        |  |  |     |    |   └org.springframework.boot.autoconfigure.AutoConfiguration.imports
|   |        |  |  ├─java
|   |        |  |  |  ├─com
|   |        |  |  |  |  ├─cdkjframework
|   |        |  |  |  |  |       ├─web
|   |        |  |  |  |  |       |  ├─socket
|   |        |  |  |  |  |       |  |   ├─WebSocket.java
|   |        |  |  |  |  |       |  |   ├─WebSocketUtils.java
|   |        |  |  |  |  |       |  |   ├─netty
|   |        |  |  |  |  |       |  |   |   ├─WebSocketHeartbeatHandler.java
|   |        |  |  |  |  |       |  |   |   ├─WebSocketInitializer.java
|   |        |  |  |  |  |       |  |   |   ├─WebSocketServer.java
|   |        |  |  |  |  |       |  |   |   └WebSocketServerHandler.java
|   |        |  |  |  |  |       |  |   ├─impl
|   |        |  |  |  |  |       |  |   |  └AbstractWebSocket.java
|   |        |  |  |  |  |       |  |   ├─config
|   |        |  |  |  |  |       |  |   |   ├─WebsocketAutoConfiguration.java
|   |        |  |  |  |  |       |  |   |   ├─WebSocketConfig.java
|   |        |  |  |  |  |       |  |   |   └WebSocketMarkerConfiguration.java
|   |        |  |  |  |  |       |  |   ├─annotation
|   |        |  |  |  |  |       |  |   |     └EnableAutoWebSocket.java
|   ├─wiki-sse
|   |    └pom.xml
|   ├─wiki-socket-client
|   |         ├─pom.xml
|   |         ├─src
|   |         |  ├─main
|   |         |  |  ├─resources
|   |         |  |  |     └spring.factories
|   |         |  |  ├─java
|   |         |  |  |  ├─com
|   |         |  |  |  |  ├─cdkjframework
|   |         |  |  |  |  |       ├─socket
|   |         |  |  |  |  |       |   ├─NettySocketBean.java
|   |         |  |  |  |  |       |   ├─client
|   |         |  |  |  |  |       |   |   ├─NettySocketClient.java
|   |         |  |  |  |  |       |   |   ├─config
|   |         |  |  |  |  |       |   |   |   └SocketClientConfig.java
|   ├─wiki-socket
|   |      ├─pom.xml
|   |      ├─src
|   |      |  ├─main
|   |      |  |  ├─resources
|   |      |  |  |     ├─META-INF
|   |      |  |  |     |    ├─spring
|   |      |  |  |     |    |   └org.springframework.boot.autoconfigure.AutoConfiguration.imports
|   |      |  |  ├─java
|   |      |  |  |  ├─com
|   |      |  |  |  |  ├─cdkjframework
|   |      |  |  |  |  |       ├─socket
|   |      |  |  |  |  |       |   ├─NettySocketServer.java
|   |      |  |  |  |  |       |   ├─NettySocketUtils.java
|   |      |  |  |  |  |       |   ├─listener
|   |      |  |  |  |  |       |   |    └SocketListener.java
|   |      |  |  |  |  |       |   ├─handler
|   |      |  |  |  |  |       |   |    ├─NettyChannelHandler.java
|   |      |  |  |  |  |       |   |    ├─NettyChannelInitializer.java
|   |      |  |  |  |  |       |   |    └NettyHeartbeatHandler.java
|   |      |  |  |  |  |       |   ├─config
|   |      |  |  |  |  |       |   |   ├─SocketAutoConfiguration.java
|   |      |  |  |  |  |       |   |   ├─SocketConfig.java
|   |      |  |  |  |  |       |   |   └SocketMarkerConfiguration.java
|   |      |  |  |  |  |       |   ├─annotation
|   |      |  |  |  |  |       |   |     └EnableAutoSocket.java
├─security
|    ├─pom.xml
|    ├─wiki-security
|    |       ├─pom.xml
|    |       ├─src
|    |       |  ├─main
|    |       |  |  ├─resources
|    |       |  |  |     ├─static
|    |       |  |  |     |   ├─aes.js
|    |       |  |  |     |   ├─favicon.ico
|    |       |  |  |     |   ├─index.html
|    |       |  |  |     |   ├─index.js
|    |       |  |  |     |   ├─login.png
|    |       |  |  |     |   ├─logo.png
|    |       |  |  |     |   ├─mode-ecb.js
|    |       |  |  |     |   ├─vue-resource.js
|    |       |  |  |     |   ├─vue.js
|    |       |  |  |     |   ├─style
|    |       |  |  |     |   |   └login.css
|    |       |  |  ├─java
|    |       |  |  |  ├─com
|    |       |  |  |  |  ├─cdkjframework
|    |       |  |  |  |  |       ├─security
|    |       |  |  |  |  |       |    ├─service
|    |       |  |  |  |  |       |    |    ├─ConfigureService.java
|    |       |  |  |  |  |       |    |    ├─ResourceService.java
|    |       |  |  |  |  |       |    |    ├─UserAuthenticationService.java
|    |       |  |  |  |  |       |    |    ├─UserLoginSuccessService.java
|    |       |  |  |  |  |       |    |    ├─UserRoleService.java
|    |       |  |  |  |  |       |    |    ├─WorkflowService.java
|    |       |  |  |  |  |       |    |    ├─impl
|    |       |  |  |  |  |       |    |    |  ├─AbstractUserDetailsService.java
|    |       |  |  |  |  |       |    |    |  ├─UserAuthenticationServiceImpl.java
|    |       |  |  |  |  |       |    |    |  └UserLoginSuccessServiceImpl.java
|    |       |  |  |  |  |       |    ├─handler
|    |       |  |  |  |  |       |    |    ├─UserAuthAccessDeniedHandler.java
|    |       |  |  |  |  |       |    |    ├─UserAuthenticationEntryPointHandler.java
|    |       |  |  |  |  |       |    |    ├─UserLoginFailureHandler.java
|    |       |  |  |  |  |       |    |    ├─UserLoginSuccessHandler.java
|    |       |  |  |  |  |       |    |    └UserLogoutSuccessHandler.java
|    |       |  |  |  |  |       |    ├─encrypt
|    |       |  |  |  |  |       |    |    ├─JwtAuthenticationFilter.java
|    |       |  |  |  |  |       |    |    └Md5PasswordEncoder.java
|    |       |  |  |  |  |       |    ├─controller
|    |       |  |  |  |  |       |    |     └SecurityCertificateController.java
|    |       |  |  |  |  |       |    ├─configure
|    |       |  |  |  |  |       |    |     └SecurityConfigure.java
|    |       |  |  |  |  |       |    ├─authorization
|    |       |  |  |  |  |       |    |       ├─AuthenticationFilter.java
|    |       |  |  |  |  |       |    |       ├─UserAuthenticationProvider.java
|    |       |  |  |  |  |       |    |       ├─UserPermissionEvaluator.java
|    |       |  |  |  |  |       |    |       └ValidateCodeFilter.java
├─queue
|   ├─pom.xml
|   ├─wiki-rocket-client
|   |         ├─pom.xml
|   |         ├─src
|   |         |  ├─main
|   |         |  |  ├─resources
|   |         |  |  |     ├─META-INF
|   |         |  |  |     |    ├─spring
|   |         |  |  |     |    |   └org.springframework.boot.autoconfigure.AutoConfiguration.imports
|   |         |  |  ├─java
|   |         |  |  |  ├─com
|   |         |  |  |  |  ├─cdkjframework
|   |         |  |  |  |  |       ├─rocket
|   |         |  |  |  |  |       |   ├─consumer
|   |         |  |  |  |  |       |   |    ├─AbstractMessageListener.java
|   |         |  |  |  |  |       |   |    ├─RocketConsumer.java
|   |         |  |  |  |  |       |   |    └RocketMessageListener.java
|   ├─wiki-rocket
|   |      ├─pom.xml
|   |      ├─src
|   |      |  ├─main
|   |      |  |  ├─resources
|   |      |  |  |     ├─META-INF
|   |      |  |  |     |    ├─spring
|   |      |  |  |     |    |   └org.springframework.boot.autoconfigure.AutoConfiguration.imports
|   |      |  |  ├─java
|   |      |  |  |  ├─com
|   |      |  |  |  |  ├─cdkjframework
|   |      |  |  |  |  |       ├─rocket
|   |      |  |  |  |  |       |   ├─producer
|   |      |  |  |  |  |       |   |    ├─ProducerUtils.java
|   |      |  |  |  |  |       |   |    └RocketProducer.java
|   ├─wiki-mqtt-client
|   |        ├─pom.xml
|   |        ├─src
|   |        |  ├─main
|   |        |  |  ├─resources
|   |        |  |  |     ├─META-INF
|   |        |  |  |     |    ├─spring
|   |        |  |  |     |    |   └org.springframework.boot.autoconfigure.AutoConfiguration.imports
|   |        |  |  ├─java
|   |        |  |  |  ├─com
|   |        |  |  |  |  ├─cdkjframework
|   |        |  |  |  |  |       ├─mqtt
|   |        |  |  |  |  |       |  ├─consumer
|   |        |  |  |  |  |       |  |    ├─CallbackService.java
|   |        |  |  |  |  |       |  |    ├─CdkjMqttCallback.java
|   |        |  |  |  |  |       |  |    └MqttConsumer.java
|   ├─wiki-mqtt
|   |     ├─pom.xml
|   |     ├─src
|   |     |  ├─main
|   |     |  |  ├─resources
|   |     |  |  |     ├─META-INF
|   |     |  |  |     |    ├─spring
|   |     |  |  |     |    |   └org.springframework.boot.autoconfigure.AutoConfiguration.imports
|   |     |  |  ├─java
|   |     |  |  |  ├─com
|   |     |  |  |  |  ├─cdkjframework
|   |     |  |  |  |  |       ├─mqtt
|   |     |  |  |  |  |       |  ├─producer
|   |     |  |  |  |  |       |  |    ├─MqttGateway.java
|   |     |  |  |  |  |       |  |    └MqttProducer.java
|   ├─wiki-kafka-client
|   |         ├─pom.xml
|   |         ├─src
|   |         |  ├─main
|   |         |  |  ├─resources
|   |         |  |  |     ├─META-INF
|   |         |  |  |     |    ├─spring
|   |         |  |  |     |    |   └org.springframework.boot.autoconfigure.AutoConfiguration.imports
|   |         |  |  ├─java
|   |         |  |  |  ├─com
|   |         |  |  |  |  ├─cdkjframework
|   |         |  |  |  |  |       ├─kafka
|   |         |  |  |  |  |       |   ├─consumer
|   |         |  |  |  |  |       |   |    ├─ConsumerConfiguration.java
|   |         |  |  |  |  |       |   |    ├─service
|   |         |  |  |  |  |       |   |    |    └ConsumerService.java
|   |         |  |  |  |  |       |   |    ├─listener
|   |         |  |  |  |  |       |   |    |    └ConsumerListener.java
|   |         |  |  |  |  |       |   |    ├─config
|   |         |  |  |  |  |       |   |    |   ├─KafkaClientAutoConfiguration.java
|   |         |  |  |  |  |       |   |    |   ├─KafkaClientConfig.java
|   |         |  |  |  |  |       |   |    |   └KafkaClientMarkerConfiguration.java
|   |         |  |  |  |  |       |   |    ├─annotation
|   |         |  |  |  |  |       |   |    |     └EnableAutoKafkaClient.java
|   ├─wiki-kafka
|   |     ├─pom.xml
|   |     ├─src
|   |     |  ├─main
|   |     |  |  ├─resources
|   |     |  |  |     ├─META-INF
|   |     |  |  |     |    ├─spring
|   |     |  |  |     |    |   └org.springframework.boot.autoconfigure.AutoConfiguration.imports
|   |     |  |  ├─java
|   |     |  |  |  ├─com
|   |     |  |  |  |  ├─cdkjframework
|   |     |  |  |  |  |       ├─kafka
|   |     |  |  |  |  |       |   ├─producer
|   |     |  |  |  |  |       |   |    ├─ProducerConfiguration.java
|   |     |  |  |  |  |       |   |    ├─util
|   |     |  |  |  |  |       |   |    |  └ProducerUtils.java
|   |     |  |  |  |  |       |   |    ├─config
|   |     |  |  |  |  |       |   |    |   ├─KafkaAutoConfiguration.java
|   |     |  |  |  |  |       |   |    |   ├─KafkaConfig.java
|   |     |  |  |  |  |       |   |    |   ├─KafkaMarkerConfiguration.java
|   |     |  |  |  |  |       |   |    |   └TopicConfig.java
|   |     |  |  |  |  |       |   |    ├─annotation
|   |     |  |  |  |  |       |   |    |     └EnableAutoKafka.java
├─licenses
|    ├─pom.xml
|    ├─wiki-license-verify
|    |          ├─pom.xml
|    |          ├─src
|    |          |  ├─main
|    |          |  |  ├─java
|    |          |  |  |  ├─com
|    |          |  |  |  |  ├─cdkjframework
|    |          |  |  |  |  |       ├─license
|    |          |  |  |  |  |       |    ├─verify
|    |          |  |  |  |  |       |    |   ├─manger
|    |          |  |  |  |  |       |    |   |   └LicenseVerifyManager.java
|    |          |  |  |  |  |       |    |   ├─listener
|    |          |  |  |  |  |       |    |   |    ├─CustomVerifyListener.java
|    |          |  |  |  |  |       |    |   |    └LicenseVerifyListener.java
|    |          |  |  |  |  |       |    |   ├─interceptor
|    |          |  |  |  |  |       |    |   |      └LicenseVerifyInterceptor.java
|    |          |  |  |  |  |       |    |   ├─config
|    |          |  |  |  |  |       |    |   |   ├─LicenseInterceptorConfig.java
|    |          |  |  |  |  |       |    |   |   └LicenseVerifyConfig.java
|    |          |  |  |  |  |       |    |   ├─annotion
|    |          |  |  |  |  |       |    |   |    └VerifyLicense.java
|    ├─wiki-license-core
|    |         ├─pom.xml
|    |         ├─src
|    |         |  ├─main
|    |         |  |  ├─java
|    |         |  |  |  ├─com
|    |         |  |  |  |  ├─cdkjframework
|    |         |  |  |  |  |       ├─license
|    |         |  |  |  |  |       |    ├─util
|    |         |  |  |  |  |       |    |  └LicenseCreatorUtils.java
|    |         |  |  |  |  |       |    ├─manger
|    |         |  |  |  |  |       |    |   └LicenseCreatorManager.java
|    ├─wiki-license
|    |      ├─pom.xml
|    |      ├─src
|    |      |  ├─main
|    |      |  |  ├─java
|    |      |  |  |  ├─com
|    |      |  |  |  |  ├─cdkjframework
|    |      |  |  |  |  |       ├─license
|    |      |  |  |  |  |       |    ├─util
|    |      |  |  |  |  |       |    |  ├─CommonUtils.java
|    |      |  |  |  |  |       |    |  └DateUtils.java
|    |      |  |  |  |  |       |    ├─service
|    |      |  |  |  |  |       |    |    ├─AServerInfos.java
|    |      |  |  |  |  |       |    |    ├─LinuxServerInfos.java
|    |      |  |  |  |  |       |    |    └WindowsServerInfos.java
|    |      |  |  |  |  |       |    ├─manager
|    |      |  |  |  |  |       |    |    └LicenseCustomManager.java
|    |      |  |  |  |  |       |    ├─helper
|    |      |  |  |  |  |       |    |   ├─ParamInitHelper.java
|    |      |  |  |  |  |       |    |   └ServerSerialHelper.java
|    |      |  |  |  |  |       |    ├─entity
|    |      |  |  |  |  |       |    |   ├─LicenseCreatorEntity.java
|    |      |  |  |  |  |       |    |   ├─LicenseExtraEntity.java
|    |      |  |  |  |  |       |    |   ├─LicenseResultEntity.java
|    |      |  |  |  |  |       |    |   ├─LicenseVerifyEntity.java
|    |      |  |  |  |  |       |    |   └ResponseResultEntity.java
├─lib
|  └lib.zip
├─datasource
|     ├─pom.xml
|     ├─wiki-datasource-rw
|     |         ├─pom.xml
|     |         ├─src
|     |         |  ├─main
|     |         |  |  ├─java
|     |         |  |  |  ├─com
|     |         |  |  |  |  ├─cdkjframework
|     |         |  |  |  |  |       ├─datasource
|     |         |  |  |  |  |       |     ├─rw
|     |         |  |  |  |  |       |     | ├─LogbackImpl.java
|     |         |  |  |  |  |       |     | ├─transaction
|     |         |  |  |  |  |       |     | |      └DynamicDataSourceTransactionManager.java
|     |         |  |  |  |  |       |     | ├─source
|     |         |  |  |  |  |       |     | |   └DynamicDataSource.java
|     |         |  |  |  |  |       |     | ├─holder
|     |         |  |  |  |  |       |     | |   └DynamicDataSourceHolder.java
|     |         |  |  |  |  |       |     | ├─connectivity
|     |         |  |  |  |  |       |     | |      ├─MybatisConfiguration.java
|     |         |  |  |  |  |       |     | |      └MybatisDruidDbConfiguration.java
|     |         |  |  |  |  |       |     | ├─config
|     |         |  |  |  |  |       |     | |   ├─MybatisConfig.java
|     |         |  |  |  |  |       |     | |   └MybatisReadConfig.java
|     ├─wiki-datasource-mybatis-plus
|     |              ├─pom.xml
|     |              ├─src
|     |              |  ├─main
|     |              |  |  ├─java
|     |              |  |  |  ├─com
|     |              |  |  |  |  ├─cdkjframework
|     |              |  |  |  |  |       ├─datasource
|     |              |  |  |  |  |       |     ├─mybatis
|     |              |  |  |  |  |       |     |    ├─plus
|     |              |  |  |  |  |       |     |    |  ├─connectivity
|     |              |  |  |  |  |       |     |    |  |      └MybatisPlusConfiguration.java
|     |              |  |  |  |  |       |     |    |  ├─config
|     |              |  |  |  |  |       |     |    |  |   └MybatisPlusConfig.java
|     ├─wiki-datasource-mongodb
|     |            ├─pom.xml
|     |            ├─src
|     |            |  ├─main
|     |            |  |  ├─resources
|     |            |  |  |     ├─META-INF
|     |            |  |  |     |    ├─spring
|     |            |  |  |     |    |   └org.springframework.boot.autoconfigure.AutoConfiguration.imports
|     |            |  |  ├─java
|     |            |  |  |  ├─com
|     |            |  |  |  |  ├─cdkjframework
|     |            |  |  |  |  |       ├─datasource
|     |            |  |  |  |  |       |     ├─mongodb
|     |            |  |  |  |  |       |     |    ├─repository
|     |            |  |  |  |  |       |     |    |     ├─IMongoRepository.java
|     |            |  |  |  |  |       |     |    |     ├─impl
|     |            |  |  |  |  |       |     |    |     |  └MongoRepository.java
|     |            |  |  |  |  |       |     |    ├─number
|     |            |  |  |  |  |       |     |    |   └MongoNumberUtils.java
|     |            |  |  |  |  |       |     |    ├─connectivity
|     |            |  |  |  |  |       |     |    |      └MongoConfiguration.java
|     |            |  |  |  |  |       |     |    ├─config
|     |            |  |  |  |  |       |     |    |   ├─MongoConfig.java
|     |            |  |  |  |  |       |     |    |   ├─MongodbAutoConfiguration.java
|     |            |  |  |  |  |       |     |    |   └MongoMarkerConfiguration.java
|     |            |  |  |  |  |       |     |    ├─annotation
|     |            |  |  |  |  |       |     |    |     └EnableAutoMongo.java
|     ├─wiki-datasource-jpa
|     |          ├─pom.xml
|     |          ├─src
|     |          |  ├─main
|     |          |  |  ├─resources
|     |          |  |  |     ├─META-INF
|     |          |  |  |     |    ├─spring
|     |          |  |  |     |    |   └org.springframework.boot.autoconfigure.AutoConfiguration.imports
|     |          |  |  ├─java
|     |          |  |  |  ├─com
|     |          |  |  |  |  ├─cdkjframework
|     |          |  |  |  |  |       ├─datasource
|     |          |  |  |  |  |       |     ├─jpa
|     |          |  |  |  |  |       |     |  ├─repository
|     |          |  |  |  |  |       |     |  |     ├─IRepositoryInt.java
|     |          |  |  |  |  |       |     |  |     └IRepositoryString.java
|     |          |  |  |  |  |       |     |  ├─connectivity
|     |          |  |  |  |  |       |     |  |      ├─JpaConfiguration.java
|     |          |  |  |  |  |       |     |  |      └JpaDruidDbConfiguration.java
|     |          |  |  |  |  |       |     |  ├─config
|     |          |  |  |  |  |       |     |  |   ├─JpaAutoConfiguration.java
|     |          |  |  |  |  |       |     |  |   ├─JpaConfig.java
|     |          |  |  |  |  |       |     |  |   └JpaMarkerConfiguration.java
|     |          |  |  |  |  |       |     |  ├─annotation
|     |          |  |  |  |  |       |     |  |     └EnableAutoJpa.java
|     ├─wiki-datasource-elasticsearch
|     |               ├─pom.xml
|     |               ├─src
|     |               |  ├─main
|     |               |  |  ├─java
|     |               |  |  |  ├─com
|     |               |  |  |  |  ├─cdkjframework
|     |               |  |  |  |  |       ├─datasource
|     |               |  |  |  |  |       |     ├─elasticsearch
|     |               |  |  |  |  |       |     |       ├─connectivity
|     |               |  |  |  |  |       |     |       |      └ElasticsearchConfiguration.java
|     |               |  |  |  |  |       |     |       ├─config
|     |               |  |  |  |  |       |     |       |   └ElasticSearchConfig.java
|     ├─wiki-datasource
|     |        ├─pom.xml
|     |        ├─src
|     |        |  ├─main
|     |        |  |  ├─resources
|     |        |  |  |     ├─META-INF
|     |        |  |  |     |    ├─spring
|     |        |  |  |     |    |   └org.springframework.boot.autoconfigure.AutoConfiguration.imports
|     |        |  |  ├─java
|     |        |  |  |  ├─com
|     |        |  |  |  |  ├─cdkjframework
|     |        |  |  |  |  |       ├─datasource
|     |        |  |  |  |  |       |     ├─mybatis
|     |        |  |  |  |  |       |     |    ├─LogbackImpl.java
|     |        |  |  |  |  |       |     |    ├─connectivity
|     |        |  |  |  |  |       |     |    |      ├─MybatisConfiguration.java
|     |        |  |  |  |  |       |     |    |      └MybatisDruidDbConfiguration.java
|     |        |  |  |  |  |       |     |    ├─config
|     |        |  |  |  |  |       |     |    |   ├─MybatisAutoConfiguration.java
|     |        |  |  |  |  |       |     |    |   ├─MybatisConfig.java
|     |        |  |  |  |  |       |     |    |   └MybatisMarkerConfiguration.java
|     |        |  |  |  |  |       |     |    ├─annotation
|     |        |  |  |  |  |       |     |    |     └EnableAutoMybatis.java
├─wiki-wechat
|      ├─pom.xml
|      ├─src
|      |  ├─main
|      |  |  ├─java
|      |  |  |  ├─com
|      |  |  |  |  ├─cdkjframework
|      |  |  |  |  |       ├─pay
|      |  |  |  |  |       |  ├─PayConfigService.java
|      |  |  |  |  |       |  ├─PaymentNotifyService.java
|      |  |  |  |  |       |  ├─PaymentService.java
|      |  |  |  |  |       |  ├─PayRecordService.java
|      |  |  |  |  |       |  ├─webchat
|      |  |  |  |  |       |  |    ├─PayRequest.java
|      |  |  |  |  |       |  |    ├─qrcode
|      |  |  |  |  |       |  |    |   ├─QrCodePayNotifyServiceImpl.java
|      |  |  |  |  |       |  |    |   └QrCodePayServiceImpl.java
|      |  |  |  |  |       |  |    ├─app
|      |  |  |  |  |       |  |    |  ├─AppPayNotifyService.java
|      |  |  |  |  |       |  |    |  ├─AppPayService.java
|      |  |  |  |  |       |  |    |  ├─impl
|      |  |  |  |  |       |  |    |  |  ├─AppPayNotifyServiceImpl.java
|      |  |  |  |  |       |  |    |  |  └AppPayServiceImpl.java
|      |  |  |  |  |       |  ├─vo
|      |  |  |  |  |       |  | └PaymentResultVo.java
|      |  |  |  |  |       |  ├─impl
|      |  |  |  |  |       |  |  ├─AbstractPaymentNotifyServiceImpl.java
|      |  |  |  |  |       |  |  ├─AbstractPaymentServiceImpl.java
|      |  |  |  |  |       |  |  ├─PayConfigServiceImpl.java
|      |  |  |  |  |       |  |  └PayRecordServiceImpl.java
|      |  |  |  |  |       |  ├─dto
|      |  |  |  |  |       |  |  └PaymentResultDto.java
|      |  |  |  |  |       ├─mp
|      |  |  |  |  |       | ├─service
|      |  |  |  |  |       | |    ├─MpService.java
|      |  |  |  |  |       | |    ├─impl
|      |  |  |  |  |       | |    |  └MpServiceImpl.java
|      |  |  |  |  |       | ├─enums
|      |  |  |  |  |       | |   ├─MaterialEnum.java
|      |  |  |  |  |       | |   └MpEnum.java
|      |  |  |  |  |       | ├─dto
|      |  |  |  |  |       | |  ├─MpBaseDto.java
|      |  |  |  |  |       | |  ├─MpDraftDto.java
|      |  |  |  |  |       | |  ├─MpMenusDto.java
|      |  |  |  |  |       | |  └MpResultDto.java
|      |  |  |  |  |       | ├─config
|      |  |  |  |  |       | |   ├─MpAddressConfig.java
|      |  |  |  |  |       | |   └MpConfig.java
├─wiki-web
|    ├─pom.xml
|    ├─src
|    |  ├─main
|    |  |  ├─resources
|    |  |  |     ├─application-dev.properties
|    |  |  |     └application.properties
|    |  |  ├─java
|    |  |  |  ├─com
|    |  |  |  |  ├─cdkjframework
|    |  |  |  |  |       ├─web
|    |  |  |  |  |       |  ├─SubscribeMessage.java
|    |  |  |  |  |       |  ├─WebApplication.java
|    |  |  |  |  |       |  ├─entity
|    |  |  |  |  |       |  |   ├─ComparisonVo.java
|    |  |  |  |  |       |  |   └PageVo.java
|    |  |  |  |  |       |  ├─controller
|    |  |  |  |  |       |  |     └TestController.java
├─wiki-util
|     ├─pom.xml
|     ├─src
|     |  ├─main
|     |  |  ├─java
|     |  |  |  ├─com
|     |  |  |  |  ├─cdkjframework
|     |  |  |  |  |       ├─util
|     |  |  |  |  |       |  ├─tool
|     |  |  |  |  |       |  |  ├─AnalysisUtils.java
|     |  |  |  |  |       |  |  ├─AssertUtils.java
|     |  |  |  |  |       |  |  ├─ByteUtils.java
|     |  |  |  |  |       |  |  ├─CollectUtils.java
|     |  |  |  |  |       |  |  ├─CompareUtils.java
|     |  |  |  |  |       |  |  ├─CopyUtils.java
|     |  |  |  |  |       |  |  ├─GoogleAuthenticatorUtils.java
|     |  |  |  |  |       |  |  ├─GzipUtils.java
|     |  |  |  |  |       |  |  ├─HexUtils.java
|     |  |  |  |  |       |  |  ├─HostUtils.java
|     |  |  |  |  |       |  |  ├─JsonUtils.java
|     |  |  |  |  |       |  |  ├─OsUtils.java
|     |  |  |  |  |       |  |  ├─PositionUtils.java
|     |  |  |  |  |       |  |  ├─RegexUtils.java
|     |  |  |  |  |       |  |  ├─ScriptUtils.java
|     |  |  |  |  |       |  |  ├─SecurityRandomUtils.java
|     |  |  |  |  |       |  |  ├─SendMailUtils.java
|     |  |  |  |  |       |  |  ├─StringUtils.java
|     |  |  |  |  |       |  |  ├─validate
|     |  |  |  |  |       |  |  |    └ValidateUtils.java
|     |  |  |  |  |       |  |  ├─number
|     |  |  |  |  |       |  |  |   ├─ConvertUtils.java
|     |  |  |  |  |       |  |  |   ├─DecimalUtils.java
|     |  |  |  |  |       |  |  |   ├─DoubleUtils.java
|     |  |  |  |  |       |  |  |   ├─FloatUtils.java
|     |  |  |  |  |       |  |  |   └IntegerUtils.java
|     |  |  |  |  |       |  |  ├─meta
|     |  |  |  |  |       |  |  |  ├─BeanRegistrationUtils.java
|     |  |  |  |  |       |  |  |  ├─ClassMetadataUtils.java
|     |  |  |  |  |       |  |  |  └StandardClassMetadata.java
|     |  |  |  |  |       |  |  ├─mapper
|     |  |  |  |  |       |  |  |   ├─ComparisonUtils.java
|     |  |  |  |  |       |  |  |   ├─FieldMappingUtils.java
|     |  |  |  |  |       |  |  |   ├─MapperUtils.java
|     |  |  |  |  |       |  |  |   └ReflectionUtils.java
|     |  |  |  |  |       |  ├─push
|     |  |  |  |  |       |  |  ├─JpushUtils.java
|     |  |  |  |  |       |  |  ├─TencentPushUtils.java
|     |  |  |  |  |       |  |  └UniPushUtils.java
|     |  |  |  |  |       |  ├─network
|     |  |  |  |  |       |  |    ├─RequestUtils.java
|     |  |  |  |  |       |  |    ├─ResponseUtils.java
|     |  |  |  |  |       |  |    ├─https
|     |  |  |  |  |       |  |    |   ├─HttpsClientUtils.java
|     |  |  |  |  |       |  |    |   └TlsPool.java
|     |  |  |  |  |       |  |    ├─http
|     |  |  |  |  |       |  |    |  ├─ClientLoginInterceptor.java
|     |  |  |  |  |       |  |    |  ├─HttpRequestUtils.java
|     |  |  |  |  |       |  |    |  ├─HttpServletUtils.java
|     |  |  |  |  |       |  |    |  └WebServiceUtils.java
|     |  |  |  |  |       |  ├─mockito
|     |  |  |  |  |       |  |    └AbstractMockitoUtils.java
|     |  |  |  |  |       |  ├─make
|     |  |  |  |  |       |  |  ├─AbstractUUIDGenerator.java
|     |  |  |  |  |       |  |  ├─AmountUtils.java
|     |  |  |  |  |       |  |  ├─ChineseToEnglishUtils.java
|     |  |  |  |  |       |  |  ├─GeneratedValueUtils.java
|     |  |  |  |  |       |  |  ├─IdCardUtils.java
|     |  |  |  |  |       |  |  ├─NnumberUtils.java
|     |  |  |  |  |       |  |  ├─SnowflakeUtils.java
|     |  |  |  |  |       |  |  └VerifyCodeUtils.java
|     |  |  |  |  |       |  ├─log
|     |  |  |  |  |       |  |  ├─LogConfigurationFactory.java
|     |  |  |  |  |       |  |  └LogUtils.java
|     |  |  |  |  |       |  ├─kryo
|     |  |  |  |  |       |  |  ├─AbstractKryoCheckUtil.java
|     |  |  |  |  |       |  |  ├─ExpKryo.java
|     |  |  |  |  |       |  |  ├─KryoInstance.java
|     |  |  |  |  |       |  |  └KryoUtil.java
|     |  |  |  |  |       |  ├─fun
|     |  |  |  |  |       |  |  └Function.java
|     |  |  |  |  |       |  ├─files
|     |  |  |  |  |       |  |   ├─AliCloudOssUtils.java
|     |  |  |  |  |       |  |   ├─FileUtils.java
|     |  |  |  |  |       |  |   ├─NotepadUtils.java
|     |  |  |  |  |       |  |   ├─XmlUtils.java
|     |  |  |  |  |       |  |   ├─ZipUtils.java
|     |  |  |  |  |       |  |   ├─pdf
|     |  |  |  |  |       |  |   |  └PdfUtils.java
|     |  |  |  |  |       |  |   ├─images
|     |  |  |  |  |       |  |   |   ├─ThumbnailUtils.java
|     |  |  |  |  |       |  |   |   ├─pictures
|     |  |  |  |  |       |  |   |   |    └PicturesUtil.java
|     |  |  |  |  |       |  |   |   ├─code
|     |  |  |  |  |       |  |   |   |  ├─BarCodeUtils.java
|     |  |  |  |  |       |  |   |   |  ├─BufferedImageLuminanceSource.java
|     |  |  |  |  |       |  |   |   |  └QrCodeUtils.java
|     |  |  |  |  |       |  |   ├─freemarker
|     |  |  |  |  |       |  |   |     └FreemarkerUtils.java
|     |  |  |  |  |       |  |   ├─excel
|     |  |  |  |  |       |  |   |   ├─EasyExcelUtils.java
|     |  |  |  |  |       |  |   |   ├─ExcelListener.java
|     |  |  |  |  |       |  |   |   ├─TestExcelEntity.java
|     |  |  |  |  |       |  |   |   ├─handler
|     |  |  |  |  |       |  |   |   |    ├─CustomCellStyleStrategy.java
|     |  |  |  |  |       |  |   |   |    ├─CustomCellWriteHeightStrategy.java
|     |  |  |  |  |       |  |   |   |    ├─CustomCellWriteWeightStrategy.java
|     |  |  |  |  |       |  |   |   |    ├─CustomMergeStrategy.java
|     |  |  |  |  |       |  |   |   |    └CustomSheetWriteHandler.java
|     |  |  |  |  |       |  |   |   ├─converter
|     |  |  |  |  |       |  |   |   |     ├─LocalDateConverter.java
|     |  |  |  |  |       |  |   |   |     └LocalDateTimeConverter.java
|     |  |  |  |  |       |  ├─executor
|     |  |  |  |  |       |  |    └ExecutorBeanUtils.java
|     |  |  |  |  |       |  ├─encrypts
|     |  |  |  |  |       |  |    ├─AesUtils.java
|     |  |  |  |  |       |  |    ├─Base64Utils.java
|     |  |  |  |  |       |  |    ├─DesensitizationUtils.java
|     |  |  |  |  |       |  |    ├─DesUtils.java
|     |  |  |  |  |       |  |    ├─JwtUtils.java
|     |  |  |  |  |       |  |    ├─Md5Utils.java
|     |  |  |  |  |       |  |    ├─RsaUtils.java
|     |  |  |  |  |       |  |    ├─UnicodeUtils.java
|     |  |  |  |  |       |  |    ├─WebChatPayAutographUtils.java
|     |  |  |  |  |       |  |    ├─weixin
|     |  |  |  |  |       |  |    |   ├─AesException.java
|     |  |  |  |  |       |  |    |   ├─ByteGroup.java
|     |  |  |  |  |       |  |    |   ├─PKCS7Encoder.java
|     |  |  |  |  |       |  |    |   ├─SHA1.java
|     |  |  |  |  |       |  |    |   ├─WXBizMsgCrypt.java
|     |  |  |  |  |       |  |    |   └XMLParse.java
|     |  |  |  |  |       |  |    ├─crc
|     |  |  |  |  |       |  |    |  ├─AlgoParams.java
|     |  |  |  |  |       |  |    |  ├─Crc16.java
|     |  |  |  |  |       |  |    |  ├─Crc32.java
|     |  |  |  |  |       |  |    |  ├─Crc64.java
|     |  |  |  |  |       |  |    |  ├─Crc8.java
|     |  |  |  |  |       |  |    |  └CrcCalculatorUtils.java
|     |  |  |  |  |       |  |    ├─china
|     |  |  |  |  |       |  |    |   ├─ChinaKeyUtils.java
|     |  |  |  |  |       |  |    |   ├─KeyUtils.java
|     |  |  |  |  |       |  |    |   └SM4Utils.java
|     |  |  |  |  |       |  ├─date
|     |  |  |  |  |       |  |  ├─CalendarUtils.java
|     |  |  |  |  |       |  |  ├─DateUtils.java
|     |  |  |  |  |       |  |  ├─LocalDateUtils.java
|     |  |  |  |  |       |  |  ├─deserializer
|     |  |  |  |  |       |  |  |      └DateJsonDeserializerUtils.java
|     |  |  |  |  |       ├─exceptions
|     |  |  |  |  |       |     ├─GlobalException.java
|     |  |  |  |  |       |     ├─GlobalRuntimeException.java
|     |  |  |  |  |       |     └UserRuntimeException.java
|     |  |  |  |  |       ├─enums
|     |  |  |  |  |       |   ├─AlgorithmTypeEnums.java
|     |  |  |  |  |       |   └PlatformEnums.java
|     |  |  |  |  |       ├─constant
|     |  |  |  |  |       |    ├─Application.java
|     |  |  |  |  |       |    ├─BusinessConsts.java
|     |  |  |  |  |       |    ├─EncodingConsts.java
|     |  |  |  |  |       |    ├─IntegerConsts.java
|     |  |  |  |  |       |    ├─LoginTypeConsts.java
|     |  |  |  |  |       |    └WebChatPayConsts.java
|     |  |  |  |  |       ├─config
|     |  |  |  |  |       |   ├─AccountConfig.java
|     |  |  |  |  |       |   ├─AliCloudOssConfig.java
|     |  |  |  |  |       |   ├─AliCloudRocketMqConfig.java
|     |  |  |  |  |       |   ├─CustomConfig.java
|     |  |  |  |  |       |   ├─DataSourceConfig.java
|     |  |  |  |  |       |   ├─InterceptorConfig.java
|     |  |  |  |  |       |   ├─JwtSecretConfig.java
|     |  |  |  |  |       |   ├─LogConfig.java
|     |  |  |  |  |       |   ├─MailConfig.java
|     |  |  |  |  |       |   ├─MqttConfig.java
|     |  |  |  |  |       |   ├─SmsConfig.java
|     |  |  |  |  |       |   ├─ThumbnailConfig.java
|     |  |  |  |  |       |   ├─TlsConfig.java
|     |  |  |  |  |       |   ├─UploadConfig.java
|     |  |  |  |  |       |   ├─VersionConfig.java
|     |  |  |  |  |       |   └WeChatConfig.java
├─wiki-swagger
|      ├─pom.xml
|      ├─src
|      |  ├─main
|      |  |  ├─resources
|      |  |  |     ├─META-INF
|      |  |  |     |    ├─spring
|      |  |  |     |    |   └org.springframework.boot.autoconfigure.AutoConfiguration.imports
|      |  |  ├─java
|      |  |  |  ├─com
|      |  |  |  |  ├─cdkjframework
|      |  |  |  |  |       ├─swagger
|      |  |  |  |  |       |    ├─SwaggerStartTrigger.java
|      |  |  |  |  |       |    ├─document
|      |  |  |  |  |       |    |    └SwaggerDocument.java
|      |  |  |  |  |       |    ├─config
|      |  |  |  |  |       |    |   ├─SwaggerAutoConfiguration.java
|      |  |  |  |  |       |    |   ├─SwaggerConfig.java
|      |  |  |  |  |       |    |   └SwaggerMarkerConfiguration.java
|      |  |  |  |  |       |    ├─annotation
|      |  |  |  |  |       |    |     └EnableAutoSwagger.java
├─wiki-pom
|    └pom.xml
├─wiki-minio
|     ├─pom.xml
|     ├─src
|     |  ├─main
|     |  |  ├─resources
|     |  |  |     ├─META-INF
|     |  |  |     |    ├─spring
|     |  |  |     |    |   └org.springframework.boot.autoconfigure.AutoConfiguration.imports
|     |  |  ├─java
|     |  |  |  ├─com
|     |  |  |  |  ├─cdkjframework
|     |  |  |  |  |       ├─minio
|     |  |  |  |  |       |   ├─MinioUtils.java
|     |  |  |  |  |       |   ├─enums
|     |  |  |  |  |       |   |   └ContentTypeEnums.java
|     |  |  |  |  |       |   ├─connectivity
|     |  |  |  |  |       |   |      └MinioConfiguration.java
|     |  |  |  |  |       |   ├─config
|     |  |  |  |  |       |   |   ├─MinioAutoConfiguration.java
|     |  |  |  |  |       |   |   ├─MinioMarkerConfiguration.java
|     |  |  |  |  |       |   |   └MinioProperties.java
|     |  |  |  |  |       |   ├─annotation
|     |  |  |  |  |       |   |     └EnableAutoMinio.java
├─wiki-message
|      ├─pom.xml
|      ├─src
|      |  ├─main
|      |  |  ├─java
|      |  |  |  ├─com
|      |  |  |  |  ├─cdkjframework
|      |  |  |  |  |       ├─message
|      |  |  |  |  |       |    ├─sms
|      |  |  |  |  |       |    |  ├─aliyun
|      |  |  |  |  |       |    |  |   └AliCloudSmsUtils.java
|      |  |  |  |  |       |    ├─call
|      |  |  |  |  |       |    |  └AliCloudCallUtils.java
├─wiki-login
|     ├─pom.xml
|     ├─src
|     |  ├─main
|     |  |  ├─java
|     |  |  |  ├─com
|     |  |  |  |  ├─cdkjframework
|     |  |  |  |  |       ├─login
|     |  |  |  |  |       |   ├─controller
|     |  |  |  |  |       |   |     └LoginController.java
|     |  |  |  |  |       |   ├─config
|     |  |  |  |  |       |   |   ├─LoginConfig.java
|     |  |  |  |  |       |   |   └Test.java
├─wiki-log
|    ├─pom.xml
|    ├─src
|    |  ├─main
|    |  |  ├─java
|    |  |  |  ├─com
|    |  |  |  |  ├─cdkjframework
|    |  |  |  |  |       ├─log
|    |  |  |  |  |       |  ├─aop
|    |  |  |  |  |       |  |  ├─AbstractBaseAopAspect.java
|    |  |  |  |  |       |  |  ├─IBaseAopAspect.java
|    |  |  |  |  |       |  |  ├─LogAopAspect.java
|    |  |  |  |  |       |  |  ├─mapper
|    |  |  |  |  |       |  |  |   └MapperDebugAspect.java
|    |  |  |  |  |       |  |  ├─enums
|    |  |  |  |  |       |  |  |   └MethodEnums.java
|    |  |  |  |  |       |  |  ├─controller
|    |  |  |  |  |       |  |  |     └ControllerDebugAspect.java
├─wiki-entity
|      ├─pom.xml
|      ├─src
|      |  ├─main
|      |  |  ├─java
|      |  |  |  ├─com
|      |  |  |  |  ├─cdkjframework
|      |  |  |  |  |       ├─entity
|      |  |  |  |  |       |   ├─BaseEntity.java
|      |  |  |  |  |       |   ├─ComparisonEntity.java
|      |  |  |  |  |       |   ├─PageEntity.java
|      |  |  |  |  |       |   ├─wechat
|      |  |  |  |  |       |   |   ├─EncryptionEntity.java
|      |  |  |  |  |       |   |   ├─WeChatVerifyTicketEntity.java
|      |  |  |  |  |       |   |   ├─response
|      |  |  |  |  |       |   |   |    ├─AuthorizationInfoEntity.java
|      |  |  |  |  |       |   |   |    └WeChatResponseEntity.java
|      |  |  |  |  |       |   |   ├─request
|      |  |  |  |  |       |   |   |    └WeChatRequestEntity.java
|      |  |  |  |  |       |   ├─user
|      |  |  |  |  |       |   |  ├─AbstractUserEntity.java
|      |  |  |  |  |       |   |  ├─BmsConfigureEntity.java
|      |  |  |  |  |       |   |  ├─JwtEntity.java
|      |  |  |  |  |       |   |  ├─OrganizationEntity.java
|      |  |  |  |  |       |   |  ├─ResourceEntity.java
|      |  |  |  |  |       |   |  ├─RoleEntity.java
|      |  |  |  |  |       |   |  ├─UserEntity.java
|      |  |  |  |  |       |   |  ├─UserRoleEntity.java
|      |  |  |  |  |       |   |  ├─UserThirdPartyEntity.java
|      |  |  |  |  |       |   |  ├─WorkflowEntity.java
|      |  |  |  |  |       |   |  ├─security
|      |  |  |  |  |       |   |  |    ├─AuthenticationEntity.java
|      |  |  |  |  |       |   |  |    ├─RmsClientDetailsEntity.java
|      |  |  |  |  |       |   |  |    └SecurityUserEntity.java
|      |  |  |  |  |       |   ├─swagger
|      |  |  |  |  |       |   |    ├─SwaggerApiInfoEntity.java
|      |  |  |  |  |       |   |    └SwaggerHeaderEntity.java
|      |  |  |  |  |       |   ├─socket
|      |  |  |  |  |       |   |   └WebSocketEntity.java
|      |  |  |  |  |       |   ├─sms
|      |  |  |  |  |       |   |  ├─BaseSmsEntity.java
|      |  |  |  |  |       |   |  ├─SendSmsEntity.java
|      |  |  |  |  |       |   |  ├─SmsResponseEntity.java
|      |  |  |  |  |       |   |  ├─SmsSendDetailEntity.java
|      |  |  |  |  |       |   |  ├─SmsSignEntity.java
|      |  |  |  |  |       |   |  ├─SmsSignFileEntity.java
|      |  |  |  |  |       |   |  ├─SmsTemplateEntity.java
|      |  |  |  |  |       |   |  ├─subscribe
|      |  |  |  |  |       |   |  |     ├─SignReportEntity.java
|      |  |  |  |  |       |   |  |     ├─SmsReportEntity.java
|      |  |  |  |  |       |   |  |     ├─SmsUpEntity.java
|      |  |  |  |  |       |   |  |     └TemplateReportEntity.java
|      |  |  |  |  |       |   |  ├─data
|      |  |  |  |  |       |   |  |  ├─SmsDetailEntity.java
|      |  |  |  |  |       |   |  |  └SmsEntity.java
|      |  |  |  |  |       |   ├─search
|      |  |  |  |  |       |   |   └SearchEnginesEntity.java
|      |  |  |  |  |       |   ├─pay
|      |  |  |  |  |       |   |  ├─PayConfigEntity.java
|      |  |  |  |  |       |   |  ├─PayRecordEntity.java
|      |  |  |  |  |       |   |  ├─webchat
|      |  |  |  |  |       |   |  |    ├─WebChatPayActionEntity.java
|      |  |  |  |  |       |   |  |    ├─WebChatPayConfigEntity.java
|      |  |  |  |  |       |   |  |    ├─WebChatPayResultsEntity.java
|      |  |  |  |  |       |   |  |    ├─WebChatResultEntity.java
|      |  |  |  |  |       |   |  |    ├─transactions
|      |  |  |  |  |       |   |  |    |      ├─app
|      |  |  |  |  |       |   |  |    |      |  ├─AmountEntity.java
|      |  |  |  |  |       |   |  |    |      |  ├─DiscountDetailsEntity.java
|      |  |  |  |  |       |   |  |    |      |  └TransactionsAppEntity.java
|      |  |  |  |  |       |   |  |    ├─query
|      |  |  |  |  |       |   |  |    |   ├─WebChatQueryEntity.java
|      |  |  |  |  |       |   |  |    |   └WebChatQueryResultEntity.java
|      |  |  |  |  |       |   |  |    ├─impl
|      |  |  |  |  |       |   |  |    |  └WebChatEntity.java
|      |  |  |  |  |       |   |  |    ├─app
|      |  |  |  |  |       |   |  |    |  ├─AmountEntity.java
|      |  |  |  |  |       |   |  |    |  ├─GoodsBodyEntity.java
|      |  |  |  |  |       |   |  |    |  ├─NotifyResultEntity.java
|      |  |  |  |  |       |   |  |    |  ├─SceneInfoEntity.java
|      |  |  |  |  |       |   |  |    |  ├─UnifiedOrderEntity.java
|      |  |  |  |  |       |   |  |    |  └UnifiedOrderReturnEntity.java
|      |  |  |  |  |       |   |  ├─alipay
|      |  |  |  |  |       |   |  |   ├─AliPayActionEntity.java
|      |  |  |  |  |       |   |  |   ├─AliPayConfigEntity.java
|      |  |  |  |  |       |   |  |   ├─AliPayResultsEntity.java
|      |  |  |  |  |       |   |  |   ├─AliResultEntity.java
|      |  |  |  |  |       |   |  |   ├─impl
|      |  |  |  |  |       |   |  |   |  └AliEntity.java
|      |  |  |  |  |       |   ├─number
|      |  |  |  |  |       |   |   └NumberEntity.java
|      |  |  |  |  |       |   ├─mongodb
|      |  |  |  |  |       |   |    └MongodbEntity.java
|      |  |  |  |  |       |   ├─message
|      |  |  |  |  |       |   |    ├─PushEntity.java
|      |  |  |  |  |       |   |    ├─TransmissionContentEntity.java
|      |  |  |  |  |       |   |    ├─tencent
|      |  |  |  |  |       |   |    |    ├─ManufacturerEntity.java
|      |  |  |  |  |       |   |    |    └XinGeEntity.java
|      |  |  |  |  |       |   |    ├─baidu
|      |  |  |  |  |       |   |    |   ├─KafkaCreateTopicEntity.java
|      |  |  |  |  |       |   |    |   └MqttCallbackEntity.java
|      |  |  |  |  |       |   |    ├─aliyun
|      |  |  |  |  |       |   |    |   ├─RocketMqCallbackEntity.java
|      |  |  |  |  |       |   |    |   └RocketMqEntity.java
|      |  |  |  |  |       |   ├─log
|      |  |  |  |  |       |   |  ├─LogRecordDto.java
|      |  |  |  |  |       |   |  ├─LogRecordEntity.java
|      |  |  |  |  |       |   |  ├─LogRecordVo.java
|      |  |  |  |  |       |   |  ├─PermissionDto.java
|      |  |  |  |  |       |   |  ├─feign
|      |  |  |  |  |       |   |  |   ├─FeignLogDto.java
|      |  |  |  |  |       |   |  |   └FeignLogEntity.java
|      |  |  |  |  |       |   ├─http
|      |  |  |  |  |       |   |  ├─HttpRequestEntity.java
|      |  |  |  |  |       |   |  ├─MockitoEntity.java
|      |  |  |  |  |       |   |  └WebServiceEntity.java
|      |  |  |  |  |       |   ├─generate
|      |  |  |  |  |       |   |    ├─OrderNumberEntity.java
|      |  |  |  |  |       |   |    ├─template
|      |  |  |  |  |       |   |    |    ├─DatabaseEntity.java
|      |  |  |  |  |       |   |    |    ├─GenerateEntity.java
|      |  |  |  |  |       |   |    |    ├─TableColumnEntity.java
|      |  |  |  |  |       |   |    |    ├─TableEntity.java
|      |  |  |  |  |       |   |    |    ├─TemplateEntity.java
|      |  |  |  |  |       |   |    |    ├─TreeEntity.java
|      |  |  |  |  |       |   |    |    ├─children
|      |  |  |  |  |       |   |    |    |    └ChildrenEntity.java
|      |  |  |  |  |       |   ├─file
|      |  |  |  |  |       |   |  ├─FileEntity.java
|      |  |  |  |  |       |   |  └ImageEntity.java
|      |  |  |  |  |       |   ├─center
|      |  |  |  |  |       |   |   ├─CenterTableLayoutEntity.java
|      |  |  |  |  |       |   |   ├─library
|      |  |  |  |  |       |   |   |    ├─ColumnLayoutEntity.java
|      |  |  |  |  |       |   |   |    └TableLayoutEntity.java
|      |  |  |  |  |       |   ├─base
|      |  |  |  |  |       |   |  ├─BaseDto.java
|      |  |  |  |  |       |   |  ├─BaseVo.java
|      |  |  |  |  |       |   |  └MongoBaseEntity.java
|      |  |  |  |  |       ├─core
|      |  |  |  |  |       |  ├─annotation
|      |  |  |  |  |       |  |     ├─FieldMapping.java
|      |  |  |  |  |       |  |     ├─FieldMeta.java
|      |  |  |  |  |       |  |     └Log.java
|      |  |  |  |  |       ├─builder
|      |  |  |  |  |       |    ├─Builder.java
|      |  |  |  |  |       |    └ResponseBuilder.java
├─wiki-core
|     ├─pom.xml
|     ├─src
|     |  ├─main
|     |  |  ├─resources
|     |  |  |     ├─banner.txt
|     |  |  |     ├─logback-spring.xml
|     |  |  |     ├─templates
|     |  |  |     |     ├─index.ftl
|     |  |  |     |     └index.html
|     |  |  |     ├─static
|     |  |  |     |   ├─aes.js
|     |  |  |     |   ├─index.css
|     |  |  |     |   ├─index.js
|     |  |  |     |   ├─mode-ecb.js
|     |  |  |     |   ├─vue-resource.js
|     |  |  |     |   └vue.js
|     |  |  |     ├─mybatis
|     |  |  |     |    ├─security
|     |  |  |     |    |    └RmsClientDetailsMapper.xml
|     |  |  |     |    ├─pay
|     |  |  |     |    |  ├─PayConfigMapper.xml
|     |  |  |     |    |  └PayRecordMapper.xml
|     |  |  |     |    ├─number
|     |  |  |     |    |   └OrderNumberMapper.xml
|     |  |  |     |    ├─generate
|     |  |  |     |    |    ├─GenerateMapper.xml
|     |  |  |     |    |    └UpdateLibraryMapper.xml
|     |  |  |     ├─com
|     |  |  |     |  ├─cdkjframework
|     |  |  |     |  |       ├─templates
|     |  |  |     |  |       |     ├─controller.ftl
|     |  |  |     |  |       |     ├─dto.ftl
|     |  |  |     |  |       |     ├─entity.ftl
|     |  |  |     |  |       |     ├─extend.ftl
|     |  |  |     |  |       |     ├─extendXml.ftl
|     |  |  |     |  |       |     ├─interface.ftl
|     |  |  |     |  |       |     ├─mapper.ftl
|     |  |  |     |  |       |     ├─mapperXml.ftl
|     |  |  |     |  |       |     ├─repository.ftl
|     |  |  |     |  |       |     ├─repositoryInt.ftl
|     |  |  |     |  |       |     ├─service.ftl
|     |  |  |     |  |       |     ├─vo.ftl
|     |  |  |     |  |       |     ├─ui
|     |  |  |     |  |       |     | ├─vo.ftl
|     |  |  |     |  |       |     | ├─table
|     |  |  |     |  |       |     | |   ├─table.ftl
|     |  |  |     |  |       |     | |   └table.tsx.ftl
|     |  |  |     |  |       |     | ├─form
|     |  |  |     |  |       |     | |  ├─form.ftl
|     |  |  |     |  |       |     | |  └form.tsx.ftl
|     |  |  |     |  |       ├─export
|     |  |  |     |  |       |   ├─xls
|     |  |  |     |  |       |   |  ├─import.xml
|     |  |  |     |  |       |   |  ├─template.xls
|     |  |  |     |  |       |   |  └template.xml
|     |  |  |     |  |       |   ├─ftl
|     |  |  |     |  |       |   |  ├─body.ftl
|     |  |  |     |  |       |   |  ├─footer.ftl
|     |  |  |     |  |       |   |  └header.ftl
|     |  |  ├─java
|     |  |  |  ├─com
|     |  |  |  |  ├─cdkjframework
|     |  |  |  |  |       ├─core
|     |  |  |  |  |       |  ├─spring
|     |  |  |  |  |       |  |   ├─ApplicationService.java
|     |  |  |  |  |       |  |   ├─CdkjApplication.java
|     |  |  |  |  |       |  |   ├─web
|     |  |  |  |  |       |  |   |  └WebConfigurer.java
|     |  |  |  |  |       |  |   ├─format
|     |  |  |  |  |       |  |   |   ├─DateFormatConfiguration.java
|     |  |  |  |  |       |  |   |   └MultipartConfiguration.java
|     |  |  |  |  |       |  |   ├─filter
|     |  |  |  |  |       |  |   |   ├─FilterHandlerInterceptor.java
|     |  |  |  |  |       |  |   |   └WebMvcFilterConfigurerAdapter.java
|     |  |  |  |  |       |  |   ├─exception
|     |  |  |  |  |       |  |   |     └OverallSituationExceptionHandler.java
|     |  |  |  |  |       |  |   ├─cors
|     |  |  |  |  |       |  |   |  ├─CdkjCorsFilter.java
|     |  |  |  |  |       |  |   |  └CustomeizedRequest.java
|     |  |  |  |  |       |  |   ├─body
|     |  |  |  |  |       |  |   |  ├─BodyHandler.java
|     |  |  |  |  |       |  |   |  ├─GlobalRequestHandler.java
|     |  |  |  |  |       |  |   |  ├─GlobalResponseHandler.java
|     |  |  |  |  |       |  |   |  ├─HttpInputMessageAdvice.java
|     |  |  |  |  |       |  |   |  └MapperObjectHandler.java
|     |  |  |  |  |       |  ├─processor
|     |  |  |  |  |       |  |     └AbstractCdkjProcessor.java
|     |  |  |  |  |       |  ├─member
|     |  |  |  |  |       |  |   └CurrentUser.java
|     |  |  |  |  |       |  ├─controller
|     |  |  |  |  |       |  |     ├─AbstractController.java
|     |  |  |  |  |       |  |     ├─IController.java
|     |  |  |  |  |       |  |     └WebUiController.java
|     |  |  |  |  |       |  ├─business
|     |  |  |  |  |       |  |    ├─mapper
|     |  |  |  |  |       |  |    |   ├─GenerateMapper.java
|     |  |  |  |  |       |  |    |   ├─OrderNumberMapper.java
|     |  |  |  |  |       |  |    |   ├─PayConfigMapper.java
|     |  |  |  |  |       |  |    |   ├─PayRecordMapper.java
|     |  |  |  |  |       |  |    |   ├─RmsClientDetailsMapper.java
|     |  |  |  |  |       |  |    |   └UpdateLibraryMapper.java
|     |  |  |  |  |       |  ├─base
|     |  |  |  |  |       |  |  ├─service
|     |  |  |  |  |       |  |  |    └BasicService.java
|     |  |  |  |  |       |  |  ├─mapper
|     |  |  |  |  |       |  |  |   └BaseMapper.java
|     |  |  |  |  |       |  ├─annotation
|     |  |  |  |  |       |  |     ├─EnableMockito.java
|     |  |  |  |  |       |  |     ├─EntityValidate.java
|     |  |  |  |  |       |  |     └Validate.java
├─wiki-constant
|       ├─pom.xml
|       ├─src
|       |  ├─main
|       |  |  ├─java
|       |  |  |  ├─com
|       |  |  |  |  ├─cdkjframework
|       |  |  |  |  |       ├─enums
|       |  |  |  |  |       |   ├─AliCloudRocketMqErrorEnums.java
|       |  |  |  |  |       |   ├─AnnotationEnums.java
|       |  |  |  |  |       |   ├─BaiDuCloudKafkaEnums.java
|       |  |  |  |  |       |   ├─DictionaryTypeEnums.java
|       |  |  |  |  |       |   ├─ERegexTypeEnums.java
|       |  |  |  |  |       |   ├─FileTypeEnums.java
|       |  |  |  |  |       |   ├─HttpMethodEnums.java
|       |  |  |  |  |       |   ├─InterfaceEnum.java
|       |  |  |  |  |       |   ├─InterfaceTypeEnums.java
|       |  |  |  |  |       |   ├─MongoQueryEnums.java
|       |  |  |  |  |       |   ├─PaymentEnums.java
|       |  |  |  |  |       |   ├─QueueMessageTypeEnums.java
|       |  |  |  |  |       |   ├─ResponseBuilderEnums.java
|       |  |  |  |  |       |   ├─UserTypeEnums.java
|       |  |  |  |  |       |   ├─sms
|       |  |  |  |  |       |   |  ├─AliSmsActionEnums.java
|       |  |  |  |  |       |   |  ├─AliSmsEnums.java
|       |  |  |  |  |       |   |  └AliSmsTemplateEnums.java
|       |  |  |  |  |       |   ├─push
|       |  |  |  |  |       |   |  ├─PushPlatformEnums.java
|       |  |  |  |  |       |   |  └PushRangeEnums.java
|       |  |  |  |  |       |   ├─pay
|       |  |  |  |  |       |   |  └WeChatEnums.java
|       |  |  |  |  |       |   ├─datasource
|       |  |  |  |  |       |   |     ├─ApolloDataSourceEnums.java
|       |  |  |  |  |       |   |     ├─ApolloMongoEnums.java
|       |  |  |  |  |       |   |     ├─ApolloRedisEnums.java
|       |  |  |  |  |       |   |     ├─DataTypeContrastEnums.java
|       |  |  |  |  |       |   |     ├─DynamicDataSourceGlobal.java
|       |  |  |  |  |       |   |     ├─MySqlDataTypeContrastEnums.java
|       |  |  |  |  |       |   |     └MySqlJdbcTypeContrastEnums.java
|       |  |  |  |  |       |   ├─config
|       |  |  |  |  |       |   |   └XxlJobEnums.java
|       |  |  |  |  |       |   ├─basics
|       |  |  |  |  |       |   |   └BasicsEnum.java
|       |  |  |  |  |       ├─constant
|       |  |  |  |  |       |    ├─AutoGenerateConsts.java
|       |  |  |  |  |       |    ├─CacheConsts.java
|       |  |  |  |  |       |    ├─DataTypeConsts.java
|       |  |  |  |  |       |    ├─DateConsts.java
|       |  |  |  |  |       |    ├─FileTypeConsts.java
|       |  |  |  |  |       |    ├─HeaderConsts.java
|       |  |  |  |  |       |    ├─HttpHeaderConsts.java
|       |  |  |  |  |       |    ├─PayTypeConsts.java
|       |  |  |  |  |       |    ├─RegexConsts.java
|       |  |  |  |  |       |    ├─sms
|       |  |  |  |  |       |    |  ├─SendSmsParameterConsts.java
|       |  |  |  |  |       |    |  ├─SmsConfigureConsts.java
|       |  |  |  |  |       |    |  ├─SmsParameterConsts.java
|       |  |  |  |  |       |    |  └SmsSignParameterConsts.java
|       |  |  |  |  |       |    ├─push
|       |  |  |  |  |       |    |  └JpushConstants.java
|       |  |  |  |  |       |    ├─datasource
|       |  |  |  |  |       |    |     ├─ApolloBasicsConsts.java
|       |  |  |  |  |       |    |     └ApolloDataSourceConsts.java
├─wiki-config
|      ├─pom.xml
|      ├─src
|      |  ├─main
|      |  |  ├─java
|      |  |  |  ├─com
|      |  |  |  |  ├─cdkjframework
|      |  |  |  |  |       ├─configure
|      |  |  |  |  |       |     ├─ConfigureRefreshController.java
|      |  |  |  |  |       |     ├─GenericPostableMvcEndpoint.java
|      |  |  |  |  |       |     └LifecycleMvcEndpointAutoConfiguration.java
├─wiki-cloud
|     ├─pom.xml
|     ├─src
|     |  ├─main
|     |  |  ├─java
|     |  |  |  ├─com
|     |  |  |  |  ├─cdkjframework
|     |  |  |  |  |       ├─cloud
|     |  |  |  |  |       |   ├─service
|     |  |  |  |  |       |   |    ├─FeignService.java
|     |  |  |  |  |       |   |    ├─impl
|     |  |  |  |  |       |   |    |  └AbstractFeignServiceImpl.java
|     |  |  |  |  |       |   ├─loadbalancer
|     |  |  |  |  |       |   |      ├─LeastActiveLoadBalancer.java
|     |  |  |  |  |       |   |      ├─LoadBalancerConfiguration.java
|     |  |  |  |  |       |   |      └WeightRobinLoadBalancer.java
|     |  |  |  |  |       |   ├─config
|     |  |  |  |  |       |   |   ├─CloudConfig.java
|     |  |  |  |  |       |   |   ├─FeignApiInterceptor.java
|     |  |  |  |  |       |   |   └FeignConfig.java
|     |  |  |  |  |       |   ├─client
|     |  |  |  |  |       |   |   ├─BufferingFeignClientResponse.java
|     |  |  |  |  |       |   |   └FeignClient.java
├─wiki-center
|      ├─pom.xml
|      ├─src
|      |  ├─main
|      |  |  ├─java
|      |  |  |  ├─com
|      |  |  |  |  ├─cdkjframework
|      |  |  |  |  |       ├─center
|      |  |  |  |  |       |   ├─service
|      |  |  |  |  |       |   |    ├─GenerateService.java
|      |  |  |  |  |       |   |    ├─LogService.java
|      |  |  |  |  |       |   |    ├─OrderNumberService.java
|      |  |  |  |  |       |   |    ├─RmsClientDetailsService.java
|      |  |  |  |  |       |   |    ├─SearchEnginesService.java
|      |  |  |  |  |       |   |    ├─SmsReportService.java
|      |  |  |  |  |       |   |    ├─UpdateDatabaseService.java
|      |  |  |  |  |       |   |    ├─impl
|      |  |  |  |  |       |   |    |  ├─GenerateServiceImpl.java
|      |  |  |  |  |       |   |    |  ├─LogServiceImpl.java
|      |  |  |  |  |       |   |    |  ├─OrderNumberServiceImpl.java
|      |  |  |  |  |       |   |    |  ├─RmsClientDetailsServiceImpl.java
|      |  |  |  |  |       |   |    |  ├─SearchEnginesServiceImpl.java
|      |  |  |  |  |       |   |    |  ├─SmsReportServiceImpl.java
|      |  |  |  |  |       |   |    |  └UpdateDatabaseServiceImpl.java
|      |  |  |  |  |       |   ├─generate
|      |  |  |  |  |       |   |    ├─CdkjCoreConfigRegistrar.java
|      |  |  |  |  |       |   |    └GenerateApplicationRunner.java
|      |  |  |  |  |       |   ├─controller
|      |  |  |  |  |       |   |     ├─GenerateController.java
|      |  |  |  |  |       |   |     ├─SmsReportController.java
|      |  |  |  |  |       |   |     └VersionController.java
|      |  |  |  |  |       |   ├─annotation
|      |  |  |  |  |       |   |     └EnableAutoGenerate.java
├─cache
|   ├─pom.xml
|   ├─wiki-redisJson
|   |       ├─pom.xml
|   |       ├─src
|   |       |  ├─main
|   |       |  |  ├─java
|   |       |  |  |  ├─com
|   |       |  |  |  |  ├─cdkjframework
|   |       |  |  |  |  |       ├─redisjson
|   |       |  |  |  |  |       |     ├─RedisJsonUtils.java
|   |       |  |  |  |  |       |     ├─connectivity
|   |       |  |  |  |  |       |     |      └RedisJsonConfiguration.java
|   |       |  |  |  |  |       |     ├─config
|   |       |  |  |  |  |       |     |   └RedisJsonConfig.java
|   ├─wiki-redis
|   |     ├─pom.xml
|   |     ├─src
|   |     |  ├─main
|   |     |  |  ├─resources
|   |     |  |  |     ├─META-INF
|   |     |  |  |     |    ├─spring
|   |     |  |  |     |    |   └org.springframework.boot.autoconfigure.AutoConfiguration.imports
|   |     |  |  ├─java
|   |     |  |  |  ├─com
|   |     |  |  |  |  ├─cdkjframework
|   |     |  |  |  |  |       ├─redis
|   |     |  |  |  |  |       |   ├─RedisUtils.java
|   |     |  |  |  |  |       |   ├─subscribe
|   |     |  |  |  |  |       |   |     ├─ISubscribe.java
|   |     |  |  |  |  |       |   |     └SubscribeConsumer.java
|   |     |  |  |  |  |       |   ├─realize
|   |     |  |  |  |  |       |   |    ├─ClusterReactiveCommands.java
|   |     |  |  |  |  |       |   |    ├─ReactiveCommands.java
|   |     |  |  |  |  |       |   |    ├─RedisClusterCommands.java
|   |     |  |  |  |  |       |   |    ├─RedisClusterPubSubConnection.java
|   |     |  |  |  |  |       |   |    ├─RedisCommands.java
|   |     |  |  |  |  |       |   |    ├─RedisPubSubConnection.java
|   |     |  |  |  |  |       |   |    └SearchCommands.java
|   |     |  |  |  |  |       |   ├─number
|   |     |  |  |  |  |       |   |   └RedisNumbersUtils.java
|   |     |  |  |  |  |       |   ├─lock
|   |     |  |  |  |  |       |   |  ├─LettuceLock.java
|   |     |  |  |  |  |       |   |  ├─impl
|   |     |  |  |  |  |       |   |  |  ├─AbstractLettuceLock.java
|   |     |  |  |  |  |       |   |  |  └RedisLettuceLock.java
|   |     |  |  |  |  |       |   ├─connectivity
|   |     |  |  |  |  |       |   |      ├─BaseRedisConfiguration.java
|   |     |  |  |  |  |       |   |      ├─RedisClusterConfiguration.java
|   |     |  |  |  |  |       |   |      ├─RedisConfiguration.java
|   |     |  |  |  |  |       |   |      ├─RedisPublishConfiguration.java
|   |     |  |  |  |  |       |   |      ├─RedisStandaloneConfiguration.java
|   |     |  |  |  |  |       |   |      └RedisSubscribeConfiguration.java
|   |     |  |  |  |  |       |   ├─config
|   |     |  |  |  |  |       |   |   ├─RedisAutoConfiguration.java
|   |     |  |  |  |  |       |   |   ├─RedisConfig.java
|   |     |  |  |  |  |       |   |   └RedisMarkerConfiguration.java
|   |     |  |  |  |  |       |   ├─annotation
|   |     |  |  |  |  |       |   |     └EnableAutoRedis.java
|   ├─wiki-cache
|   |     ├─pom.xml
|   |     ├─src
|   |     |  ├─main
|   |     |  |  ├─java
|   |     |  |  |  ├─com
|   |     |  |  |  |  ├─cdkjframework
|   |     |  |  |  |  |       ├─cache
|   |     |  |  |  |  |       |   └CacheConfigurer.java

