package com.cdkjframework.swagger.customizer;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.swagger.config.SwaggerConfig;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.github.xiaoymin.knife4j.core.conf.ExtensionsConstants;
import com.github.xiaoymin.knife4j.core.conf.GlobalConstants;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jSetting;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ProjectName: common
 * @Package: com.cdkjframework.swagger.customizer
 * @ClassName: CdkjOpenApiCustomizer
 * @Author: xiaLin
 * @Description: OpenApi文档定制器
 * @Date: 2024/9/7 17:54
 * @Version: 1.0
 */
@AllArgsConstructor
@RequiredArgsConstructor
public class CdkjOpenApiCustomizer implements GlobalOpenApiCustomizer {

  /**
   * Swagger配置。
   */
  private final SwaggerConfig swaggerConfig;

  /**
   * 自定义OpenAPI文档。
   *
   * @param openApi OpenAPI对象。
   */
  @Override
  public void customise(OpenAPI openApi) {
    if (swaggerConfig.isEnable()) {
      Map<String, Object> objectMap = new HashMap<>(IntegerConsts.THREE);
      objectMap.put(GlobalConstants.EXTENSION_OPEN_SETTING_NAME, setting);
      objectMap.put(GlobalConstants.EXTENSION_OPEN_MARKDOWN_NAME, openApiExtensionResolver.getMarkdownFiles());
      openApi.addExtension(GlobalConstants.EXTENSION_OPEN_API_NAME, objectMap);
      addOrderExtension(openApi);
    }
  }
  /**
   * 往OpenAPI内tags字段添加x-order属性
   *
   * @param openApi openApi
   */
  private void addOrderExtension(OpenAPI openApi) {
    if (CollectionUtils.isEmpty(swaggerConfig.getGroupConfigs())) {
      return;
    }
    // 获取包扫描路径
    Set<String> packagesToScan =
        swaggerConfig.getGroupConfigs().stream()
            .map(SpringDocConfigProperties.GroupConfig::getPackagesToScan)
            .filter(toScan -> !CollectionUtils.isEmpty(toScan))
            .flatMap(List::stream)
            .collect(Collectors.toSet());
    if (CollectionUtils.isEmpty(packagesToScan)) {
      return;
    }
    // 扫描包下被ApiSupport注解的RestController Class
    Set<Class<?>> classes =
        packagesToScan.stream()
            .map(packageToScan -> scanPackageByAnnotation(packageToScan, RestController.class))
            .flatMap(Set::stream)
            .filter(clazz -> clazz.isAnnotationPresent(ApiSupport.class))
            .collect(Collectors.toSet());
    if (!CollectionUtils.isEmpty(classes)) {
      // ApiSupport oder值存入tagSortMap<Tag.name,ApiSupport.order>
      Map<String, Integer> tagOrderMap = new HashMap<>();
      classes.forEach(
          clazz -> {
            Tag tag = getTag(clazz);
            if (Objects.nonNull(tag)) {
              ApiSupport apiSupport = clazz.getAnnotation(ApiSupport.class);
              tagOrderMap.putIfAbsent(tag.name(), apiSupport.order());
            }
          });
      // 往openApi tags字段添加x-order增强属性
      if (openApi.getTags() != null) {
        openApi
            .getTags()
            .forEach(
                tag -> {
                  if (tagOrderMap.containsKey(tag.getName())) {
                    tag.addExtension(
                        ExtensionsConstants.EXTENSION_ORDER, tagOrderMap.get(tag.getName()));
                  }
                });
      }
    }
  }

  private Tag getTag(Class<?> clazz) {
    // 从类上获取
    Tag tag = clazz.getAnnotation(Tag.class);
    if (Objects.isNull(tag)) {
      // 从接口上获取
      Class<?>[] interfaces = clazz.getInterfaces();
      if (ArrayUtils.isNotEmpty(interfaces)) {
        for (Class<?> interfaceClazz : interfaces) {
          Tag anno = interfaceClazz.getAnnotation(Tag.class);
          if (Objects.nonNull(anno)) {
            tag = anno;
            break;
          }
        }
      }
    }
    return tag;
  }

  private Set<Class<?>> scanPackageByAnnotation(
      String packageName, final Class<? extends Annotation> annotationClass) {
    ClassPathScanningCandidateComponentProvider scanner =
        new ClassPathScanningCandidateComponentProvider(false);
    scanner.addIncludeFilter(new AnnotationTypeFilter(annotationClass));
    Set<Class<?>> classes = new HashSet<>();
    for (BeanDefinition beanDefinition : scanner.findCandidateComponents(packageName)) {
      try {
        Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
        classes.add(clazz);
      } catch (ClassNotFoundException ignore) {

      }
    }
    return classes;
  }
}
