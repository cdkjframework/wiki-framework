package com.cdkjframework.swagger;

import com.cdkjframework.constant.Application;
import com.cdkjframework.entity.swagger.SwaggerApiInfoEntity;
import com.cdkjframework.entity.swagger.SwaggerHeaderEntity;
import com.cdkjframework.swagger.config.SwaggerConfig;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.CollectUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.fasterxml.classmate.TypeResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.servers.ServerVariable;
import io.swagger.v3.oas.models.servers.ServerVariables;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: hongtu.slps.bms
 * @Package: com.cdkjframework.core.base.swagger
 * @ClassName: SwaggerStartTrigger
 * @Description: swagger2配置
 * @Author: xiaLin
 * @Version: 1.0
 */
public class SwaggerStartTrigger {

	/**
	 * 日志
	 */
	private final LogUtils LOG_UTILS = LogUtils.getLogger(SwaggerStartTrigger.class);

	/**
	 * 读取配置
	 */
	private final SwaggerConfig swaggerConfig;

	/**
	 * 类型解析程序
	 */
	private final TypeResolver typeResolver;

	/**
	 * 构建函数
	 */
	public SwaggerStartTrigger(SwaggerConfig swaggerConfig, TypeResolver typeResolver) {
		this.swaggerConfig = swaggerConfig;
		this.typeResolver = typeResolver;
	}

	/**
	 * 生成通用响应信息
	 *
	 * @return 返回结果
	 */
	private static List<Response> getGlobalResponseMessage() {
		List<Response> responses = new ArrayList<>();
		responses.add(new ResponseBuilder().code("404").description("找不到资源").build());

		// 返回结果
		return responses;
	}

	/**
	 * 创建API应用
	 * apiInfo() 增加API相关信息
	 * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
	 * 本例采用指定扫描的包路径来定义指定要建立API的目录。
	 */
	@Bean(name = "start")
	public void start() {
		if (StringUtils.isNullAndSpaceOrEmpty(swaggerConfig.getBasePackage())) {
			return;
		}
		LOG_UTILS.info("Swagger 3 开始配置：{}", LocalDateUtils.dateTimeCurrentFormatter());
		//接口信息
		List<SwaggerApiInfoEntity> apiInfoEntityList = JsonUtils
				.jsonStringToList(swaggerConfig.getBasePackage(), SwaggerApiInfoEntity.class);
		List<SwaggerHeaderEntity> headerEntityList;
		if (StringUtils.isNotNullAndEmpty(swaggerConfig.getHeaders())) {
			headerEntityList = JsonUtils
					.jsonStringToList(swaggerConfig.getHeaders(), SwaggerHeaderEntity.class);
		} else {
			headerEntityList = new ArrayList<>();
		}
		// 备用类型规则
		List<String> resolve = swaggerConfig.getResolve();
		AlternateTypeRule[] alternateTypeRules;
		if (CollectUtils.isNotEmpty(resolve)) {
			alternateTypeRules = new AlternateTypeRule[resolve.size()];
			for (String key :
					resolve) {
				try {
					Type type = Class.forName(key);
					alternateTypeRules[resolve.indexOf(key)] = AlternateTypeRules.newRule(
							typeResolver.resolve(Map.class, String.class, typeResolver.resolve(List.class, type)),
							typeResolver.resolve(Map.class, String.class, WildcardType.class), Ordered.HIGHEST_PRECEDENCE);
				} catch (ClassNotFoundException e) {
					LOG_UTILS.error(e);
				}
			}
		} else {
			alternateTypeRules = null;
		}
		final boolean hidden = swaggerConfig.getHidden();
		//设置 header
		List<RequestParameter> parameters = new ArrayList<>();
		String[] headers = new String[headerEntityList.size()];
		for (SwaggerHeaderEntity header :
				headerEntityList) {
			headers[headerEntityList.indexOf(header)] = header.getHeaderName();
			RequestParameterBuilder builderPar = new RequestParameterBuilder();
			builderPar.name(header.getHeaderName())
					.description(header.getDescription())
					.in(ParameterType.HEADER)
					.query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
					.required(Boolean.FALSE).build();
			parameters.add(builderPar.build());
		}

		for (SwaggerApiInfoEntity entity :
				apiInfoEntityList) {
			LOG_UTILS.info("Swagger 3 分组：{}", entity.getGroupName());

			BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(GroupedOpenApi.class,
					() -> {
						GroupedOpenApi openApi = GroupedOpenApi.builder()
								.group(entity.getGroupName())
								.packagesToScan(entity.getBasePackage())
								.pathsToMatch()
								.addOpenApiCustomizer(customizer -> {
									customizer.setInfo(apiInfo());
								})
								.addRouterOperationCustomizer((routerOperation, handlerMethod) -> {
									routerOperation.setHeaders(headers);
									return routerOperation;
								})
								.headersToMatch(headers)
								.build();
						// 返回结果
						return openApi;
					});
			DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) Application
					.applicationContext.getBeanFactory();
			BeanDefinition definition = definitionBuilder.getBeanDefinition();
			beanFactory.registerBeanDefinition(entity.getBeanName(), definition);
		}
		LOG_UTILS.info("Swagger 3 配置成功：{}", LocalDateUtils.dateTimeCurrentFormatter());
	}

	@Bean(name = "openAPI")
	public OpenAPI openAPI() {
		return new OpenAPI()
				//必须
				.info(apiInfo())
				.servers(List.of(new Server()
						//必须
						.url("服务器网址")
						.description("描述")
						.variables(new ServerVariables()
								.addServerVariable("变量名称", new ServerVariable()
										._enum(List.of("枚举"))
										//必须
										._default("参数值")
										.description("描述")))))
				.components(new Components())
				.externalDocs(new ExternalDocumentation()
						.description("swagger官方文档")
						.url("https://swagger.io/docs/open-source-tools/swagger-ui/usage/configuration/"));

	}

	/**
	 * 创建该API的基本信息（这些基本信息会展现在文档页面中）
	 * 访问地址：http://项目实际地址/swagger-ui.html
	 *
	 * @return 返回接口信息
	 */
	private Info apiInfo() {
		//联系人信息
		Contact contact = new Contact()
				.name(swaggerConfig.getContact())
				.url(swaggerConfig.getTermsOfServiceUrl())
				.email(swaggerConfig.getEmail());

		// 构建结果
		return new Info()
				.title(swaggerConfig.getTitle())
				.description(swaggerConfig.getDescription())
				.termsOfService(swaggerConfig.getTermsOfServiceUrl())
				.contact(contact)
				.version(swaggerConfig.getVersion());
	}
}
