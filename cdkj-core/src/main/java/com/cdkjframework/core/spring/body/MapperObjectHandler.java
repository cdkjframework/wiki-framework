package com.cdkjframework.core.spring.body;

import com.cdkjframework.util.date.LocalDateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.core.spring.body
 * @ClassName: MapperObjectHandler
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
public class MapperObjectHandler {
//
//    @Bean(name = "mapperObject")
//    public ObjectMapper getObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDateTime.class,
//                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(LocalDateUtils.DATE_HH_MM_SS)));
//        javaTimeModule.addSerializer(LocalDate.class,
//                new LocalDateSerializer(DateTimeFormatter.ofPattern(LocalDateUtils.DATE)));
//        javaTimeModule.addSerializer(LocalTime.class,
//                new LocalTimeSerializer(DateTimeFormatter.ofPattern(LocalDateUtils.TIME)));
//        objectMapper.registerModule(javaTimeModule);
//        return objectMapper;
//    }
}
