package com.cdkjframework.center.service;

import com.cdkjframework.entity.generate.template.*;
import com.cdkjframework.util.tool.HostUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.business.service
 * @ClassName: GenerateService
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface GenerateService {
    /**
     * 默认值
     */
    List<String> DEFAULT_ACTIVE = Arrays.asList("dev", "test");

    /**
     * 分割符
     */
    String DIVISION = HostUtils.getOs().startsWith("win") ? "\\" : "/";
    /**
     * JAP 模板
     */
    String JPA = "repository";
    /**
     * JAP 模板
     */
    List<String> MY_BATIS = Arrays.asList("mapper", "mapperXml", "extendXml");

    /**
		 * 模板默认值
		 */
		List<TemplateEntity> TEMPLATE_LIST = new ArrayList<>() {
			{
				add(new TemplateEntity("vo", DIVISION + "vo" + DIVISION, "Vo.java"));
				add(new TemplateEntity("ui/vo", DIVISION + "ui" + DIVISION + "form" + DIVISION, ".tsx"));
				add(new TemplateEntity("ui/form", DIVISION + "ui" + DIVISION + "form" + DIVISION, ".tsx"));
				add(new TemplateEntity("ui/table", DIVISION + "ui" + DIVISION + "table" + DIVISION, ".tsx"));
				add(new TemplateEntity("dto", DIVISION + "dto" + DIVISION, "Dto.java"));
				add(new TemplateEntity("entity", DIVISION + "entity" + DIVISION, "Entity.java"));
				add(new TemplateEntity("extend", DIVISION + "entity" + DIVISION + "extend" + DIVISION, "ExtendEntity.java"));
				add(new TemplateEntity("controller", DIVISION + "web" + DIVISION + "controller" + DIVISION, "Controller.java"));
				add(new TemplateEntity("service", DIVISION + "service" + DIVISION + "impl" + DIVISION, "ServiceImpl.java"));
				add(new TemplateEntity("interface", DIVISION + "service" + DIVISION, "Service.java"));
				add(new TemplateEntity("repository", DIVISION + "repository" + DIVISION, "Repository.java"));
				add(new TemplateEntity("repositoryInt", DIVISION + "repository" + DIVISION, "Repository.java"));
				add(new TemplateEntity("mapper", DIVISION + "mapper" + DIVISION, "Mapper.java"));
				add(new TemplateEntity("mapperXml", DIVISION + "mybatis" + DIVISION, "Mapper.xml"));
				add(new TemplateEntity("extendXml", DIVISION + "mybatis" + DIVISION + "extend" + DIVISION, "ExtendMapper.xml"));
			}
		};

    /**
     * 获取数据库
     *
     * @return 返回结果
     */
    DatabaseEntity findDatabase();

    /**
     * 获取数据库表
     *
     * @param tableEntity 查询实体
     * @return 返回结果
     */
    List<TableEntity> findTableList(TableEntity tableEntity);

    /**
     * 获取数据库表
     *
     * @param tableEntity 查询实体
     * @return 返回结果
     */
    List<TreeEntity> findDatabaseTableList(TableEntity tableEntity);

    /**
     * 获取数据库表
     *
     * @param columnEntity 查询实体
     * @return 返回结果
     */
    List<TableColumnEntity> findTableColumnList(TableColumnEntity columnEntity);

    /**
     * 生成业务代码
     *
     * @param entityList 选择结果
     * @param dataBase   数据库
     * @return 返回是否成功
     */
    Boolean generateCode(List<TreeEntity> entityList, String dataBase);
}
