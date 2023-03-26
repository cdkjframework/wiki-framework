package com.cdkjframework.entity.generate.template;

import lombok.Data;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.generate.template
 * @ClassName: TemplateEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2022/10/4 17:24
 * @Version: 1.0
 */
@Data
public class TemplateEntity {
    /**
     * 模版名称
     */
    private String templateName;
    /**
     * 目录
     */
    private String cateLog;
    /**
     * 后缀
     */
    private String suffix;

    /**
     * 构造函数
     */
    public TemplateEntity() {
    }

    /**
     * 构造函数
     *
     * @param templateName 模板名称
     * @param cateLog      目录
     * @param suffix       后缀
     */
    public TemplateEntity(String templateName, String cateLog, String suffix) {
        this.templateName = templateName;
        this.cateLog = cateLog;
        this.suffix = suffix;
    }
}
