package com.cdkjframework.entity.generate.template;


import com.cdkjframework.entity.generate.template.children.ChildrenEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cdkjframework
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
   * 根目录
   */
  private String basePath;

  /**
   * 路径
   */
  private List<String> path;

  /**
   * 是否生成 myBatis 模板
   */
  private Boolean jpa;

  /**
   * 是否生成JAP模板
   */
  private Boolean myBatis;
  /**
   * 是否整形模板
   */
  private boolean intTemplate;

  /**
   * 类名称
   */
  private String className;

  /**
   * 类名称 2
   */
  private String classLowName;
  /**
   * 路径
   */
  private String uri;

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
