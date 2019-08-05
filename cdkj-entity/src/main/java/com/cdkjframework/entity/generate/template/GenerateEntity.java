package com.cdkjframework.entity.generate.template;


import com.cdkjframework.entity.generate.template.children.ChildrenEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.entity.generate
 * @ClassName: GenerateBaseEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class GenerateEntity {

    /**
     * 返回结果
     */
    private List<String> leading = new ArrayList<>();

    /**
     * 包名
     */
    private String packageName;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 类名称
     */
    private String className;

    /**
     * 类名称 2
     */
    private String classLowName;

    /**
     * 表描述
     */
    private String description;

    /**
     * 作者
     */
    private String author;

    /**
     * 表
     */
    private String table;

    /**
     * 数据库
     */
    private String dataBase;

    /**
     * serialVersionUID
     */
    private String serialVersionUID = "-1";

    /**
     * 字段
     */
    private List<ChildrenEntity> children;
}
