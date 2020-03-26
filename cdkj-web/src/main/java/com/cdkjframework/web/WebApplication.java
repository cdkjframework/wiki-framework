package com.cdkjframework.web;

import com.cdkjframework.entity.ComparisonEntity;
import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.util.tool.CopyUtils;
import com.cdkjframework.web.entity.ComparisonVo;
import com.cdkjframework.web.entity.PageVo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web
 * @ClassName: WebApplaction
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.cdkjframework.web",
        "com.cdkjframework.center",
        "com.cdkjframework.config",
        "com.cdkjframework.core",
        "com.cdkjframework.util",
        "com.cdkjframework.entity",
        "com.cdkjframework.constant",
        "com.cdkjframework.datasource.mybatis",
        "com.cdkjframework.datasource.mongodb",
        "com.cdkjframework.core.base.swagger",
        "com.cdkjframework.redis"
})
//@EnableTransactionManagement
@Configuration
@EnableSwagger2
@MapperScan(basePackages = "com.cdkjframework.core.business.mapper")
//@EnableAutoGenerate(update = true, basePackage = "com.cdkjframework.web", projectName = "com.cdkjframework.web")
//@EnableApolloConfig
public class WebApplication {

    /**
     * 启动方法
     *
     * @param args 参数
     */
    public static void main(String[] args) {

        PageVo<ComparisonVo> pageVo = new PageVo<>();
        List<ComparisonVo> voList = new ArrayList<>();
        voList.add(new ComparisonVo());
        voList.add(new ComparisonVo());
        voList.add(new ComparisonVo());
        pageVo.setData(voList);
        pageVo.setCode(0);
        pageVo.setPageIndex(1);
        pageVo.setTotal(100);

        PageEntity<ComparisonEntity> pageEntity = CopyUtils.copyNoNullProperties(pageVo, PageEntity.class);

        SpringApplication.run(WebApplication.class, args);
    }
}
