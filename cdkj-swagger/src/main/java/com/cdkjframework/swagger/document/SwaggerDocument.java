package com.cdkjframework.swagger.document;

import com.cdkjframework.config.SwaggerConfig;
import com.cdkjframework.entity.swagger.SwaggerApiInfoEntity;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.util.ResourceUtils;

import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

/**
 * @ProjectName: common
 * @Package: com.cdkjframework.swagger.document
 * @ClassName: SwaggerDocument
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Order(Integer.MIN_VALUE + 101)
public class SwaggerDocument implements ApplicationRunner {

    /**
     * 读取配置
     */
    private final SwaggerConfig swaggerConfig;

    /**
     * 构造函数
     *
     * @param swaggerConfig 配置
     */
    public SwaggerDocument(SwaggerConfig swaggerConfig) {
        this.swaggerConfig = swaggerConfig;
    }

    /**
     * 程序端口
     */
    @Value("${server.port}")
    private Integer port;

    /**
     * 运行环境
     */
    @Value("${spring.profiles.active}")
    private String active;

    /**
     * 默认值
     */
    private String defaultActive = "dev";

    /**
     * 路径
     */
    private String asciiDocPath = "src/docs/asciidoc/generated/";

    /**
     * 用于运行bean的回调。
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        generateAsciiDocs();
    }

    /**
     * 生成Ascii文档
     *
     * @throws Exception 异常信息
     */
    public void generateAsciiDocs() throws Exception {
        if (StringUtils.isNullAndSpaceOrEmpty(swaggerConfig.getBasePackage()) ||
                StringUtils.isNullAndSpaceOrEmpty(active) ||
                port == null || port == 0 || active.toLowerCase().equals(defaultActive)) {
            return;
        }
        // 输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        //接口信息
        List<SwaggerApiInfoEntity> apiInfoEntityList = JsonUtils
                .jsonStringToList(swaggerConfig.getBasePackage(), SwaggerApiInfoEntity.class);

        for (SwaggerApiInfoEntity api :
                apiInfoEntityList) {
            Swagger2MarkupConverter.
                    from(new URL("http://127.0.0.1:" + port + "/v2/api-docs?group=" + api.getGroupName()))
                    .withConfig(config)
                    .build()
                    .toFolder(Paths.get(asciiDocPath + api.getGroupName()));
        }
    }
}
