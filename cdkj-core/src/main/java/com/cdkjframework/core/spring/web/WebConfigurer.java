package com.cdkjframework.core.spring.web;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.date.LocalDateUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.user
 * @ClassName: WebMvcConfigurer
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class WebConfigurer implements WebMvcConfigurer {

  /**
   * 扩展消息转换器
   *
   * @param converters 转换器
   */
  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//    // 全局配置序列化返回 JSON 处理
//    JavaTimeModule javaTimeModule = new JavaTimeModule();
//    String pattern = LocalDateUtils.DATE_HH_MM_SS;
//    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
//    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(LocalDateUtils.DATE);
//    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(LocalDateUtils.TIME);
//    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
//    javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
//    javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
//    javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
//    javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
//    javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
//
//    // 禁止时间字段序列化成时间戳
//    ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().modules(javaTimeModule)
//            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();
//    // 序列化对象 null 值不抛异常
//    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
//
//    // date 时间格式化
//    objectMapper.setDateFormat(new SimpleDateFormat(pattern));
//    converter.setObjectMapper(objectMapper);
//
//    converters.add(IntegerConsts.ZERO, converter);
  }
}
