package com.cdkjframework.center.service.impl;

import com.cdkjframework.center.annotation.EnableAutoGenerate;
import com.cdkjframework.center.service.GenerateService;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.core.business.mapper.GenerateMapper;
import com.cdkjframework.entity.BaseEntity;
import com.cdkjframework.entity.generate.template.*;
import com.cdkjframework.entity.generate.template.children.ChildrenEntity;
import com.cdkjframework.enums.datasource.MySqlDataTypeContrastEnums;
import com.cdkjframework.enums.datasource.MySqlJdbcTypeContrastEnums;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.files.FileUtils;
import com.cdkjframework.util.files.freemarker.FreemarkerUtil;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.CopyUtils;
import com.cdkjframework.util.tool.HostUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.meta.ClassMetadataUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.business.service.impl
 * @ClassName: GenerateServiceImpl
 * @Description: 生成服务
 * @Author: xiaLin
 * @Version: 1.0
 */

@Service
@RequiredArgsConstructor
public class GenerateServiceImpl implements GenerateService {

  /**
   * 配置
   */
  private final CustomConfig customConfig;

  /**
   * 替换值
   */
  private final String REPLACEMENT = " ";

  /**
   * 日志
   */
  private LogUtils logUtil = LogUtils.getLogger(GenerateServiceImpl.class);

  /**
   * 生成 mapper
   */
  private final GenerateMapper generateMapper;

  /**
   * 获取数据库
   *
   * @return 返回结果
   */
  @Override
  public DatabaseEntity findDatabase() {
    // 获取当前用户数据库
    List<DatabaseEntity> myDataBase;
    int data = ConvertUtils.convertInt(customConfig.getDataBase());
    if (data == IntegerConsts.ONE) {
      myDataBase = generateMapper.findDatabaseByPostgre();
    } else {
      myDataBase = generateMapper.findDatabase();
    }
    if (myDataBase == null || myDataBase.size() == 0) {
      return new DatabaseEntity();
    }

    // 获取到数据
    DatabaseEntity entity = myDataBase.get(0);
    // 获取到全部数据
    if (data == IntegerConsts.ONE) {
      entity.getChildren().add(CopyUtils.copyProperties(entity, DatabaseEntity.class));
    } else {
      List<DatabaseEntity> entityList = generateMapper.findDatabaseList();
      if (entityList != null) {
        entity.getChildren().addAll(entityList);
      }
    }

    // 返回结果
    return entity;
  }

  /**
   * 获取数据库表
   *
   * @param tableEntity 查询实体
   * @return 返回结果
   */
  @Override
  public List<TableEntity> findTableList(TableEntity tableEntity) {
    int data = ConvertUtils.convertInt(customConfig.getDataBase());
    if (data == IntegerConsts.ONE) {
      return generateMapper.findDatabaseTableListByPostgre(tableEntity);
    } else {
      return generateMapper.findDatabaseTableList(tableEntity);
    }
  }

  /**
   * 获取数据库表
   *
   * @param tableEntity 查询实体
   * @return 返回结果
   */
  @Override
  public List<TreeEntity> findDatabaseTableList(TableEntity tableEntity) {
    List<TableEntity> tableEntityList = findTableList(tableEntity);
    List<TreeEntity> treeEntityList = new ArrayList<>();
    for (TableEntity entity :
        tableEntityList) {
      TreeEntity tree = new TreeEntity();
      tree.setId(entity.getTableName());
      tree.setLabel(entity.getTableName());
      tree.setExplain(entity.getTableComment());

      // 字段
      TableColumnEntity columnEntity = new TableColumnEntity();
      columnEntity.setTableName(entity.getTableName());
      columnEntity.setTableSchema(entity.getTableSchema());
      List<TableColumnEntity> columnEntityList = findTableColumnList(columnEntity);
      for (TableColumnEntity column :
          columnEntityList) {
        TreeEntity treeColumn = new TreeEntity();
        treeColumn.setId(column.getColumnName());
        treeColumn.setLabel(column.getColumnName());
        treeColumn.setExplain(ConvertUtils.convertString(column.getColumnComment())
            .replace("\\n", REPLACEMENT)
            .replace("\\t", REPLACEMENT)
            .replace("\\s", REPLACEMENT)
            .replace("\\r", REPLACEMENT));

        // 添加子节点
        tree.getChildren().add(treeColumn);
      }

      // 添加节点
      treeEntityList.add(tree);
    }

    // 返回结果
    return treeEntityList;
  }

  /**
   * 获取数据库表
   *
   * @param columnEntity 查询实体
   * @return 返回结果
   */
  @Override
  public List<TableColumnEntity> findTableColumnList(TableColumnEntity columnEntity) {

    int data = ConvertUtils.convertInt(customConfig.getDataBase());
    if (data == IntegerConsts.ONE) {
      return generateMapper.findTableColumnListByPostgre(columnEntity);
    } else {
      return generateMapper.findTableColumnList(columnEntity);
    }
  }

  /**
   * 生成业务代码
   *
   * @param entityList 选择结果
   * @param dataBase   数据库
   * @return 返回是否成功
   */
  @Override
  public Boolean generateCode(List<TreeEntity> entityList, String dataBase) {
    boolean isGenerate = false;
    try {
      TableEntity tableEntity = new TableEntity();
      tableEntity.setTableSchema(dataBase);
      List<TreeEntity> treeEntityList = findDatabaseTableList(tableEntity);
      if (treeEntityList == null || treeEntityList.size() == 0) {
        return false;
      }
      // 获取基础类里的属性
      Field[] fields = BaseEntity.class.getDeclaredFields();
      for (TreeEntity entity :
          entityList) {
        Optional<TreeEntity> optional = treeEntityList.stream()
            .filter(f -> f.getLabel().equals(entity.getLabel()))
            .findFirst();
        if (!optional.isPresent()) {
          continue;
        }

        // 找到表
        TreeEntity treeEntity = optional.get();

        try {
          // 模板生成
          templateGeneration(treeEntity, dataBase, fields);
        } catch (Exception ex) {
          logUtil.error(ex.getStackTrace(), ex.getMessage());
        }
      }
      isGenerate = true;
    } catch (Exception ex) {
      logUtil.error(ex.getCause(), ex.getMessage());
    }

    // 返回结果
    return isGenerate;
  }

  /**
   * 模板生成
   *
   * @param treeEntity 实体
   * @param dataBase   数据库
   * @param fields     属性
   */
  private void templateGeneration(TreeEntity treeEntity, String dataBase, Field[] fields) {
    GenerateEntity entity = new GenerateEntity();

    loadData(entity, treeEntity, dataBase, fields);
    try {

      final String os = HostUtils.getOs();
      String division = os.startsWith("win") ? "\\" : "/";
      // 生成 entity
      template(entity, "entity", division + "entity" + division, "Entity.java");
      template(entity, "extend", division + "entity" + division + "extend" + division, "ExtendEntity.java");

      // 生成 dto
      template(entity, "dto", division + "dto" + division, "Dto.java");
      // 生成 vo
      template(entity, "vo", division + "vo" + division, "Vo.java");

      // 生成 service
      template(entity, "service", division + "service" + division + "impl" + division, "ServiceImpl.java");

      // 生成 service Interface
      template(entity, "interface", division + "service" + division, "Service.java");

      // 生成 mapper java
      template(entity, "mapper", division + "mapper" + division, "Mapper.java");

      // 生成 controller java
      template(entity, "controller", division + "web" + division + "controller" + division, "Controller.java");

      // 生成 mapper xml
      String xmlPath = division + "mapper" + division + "xml" + division;
      template(entity, "mapperXml", xmlPath, "Mapper.xml");

      // 生成 mapper extend xml
      xmlPath += "extend" + division;
      template(entity, "extendXml", xmlPath, "ExtendMapper.xml");
    } catch (IOException e) {
      logUtil.error(e.getCause(), e.getMessage());
    } catch (TemplateException e) {
      logUtil.error(e.getCause(), e.getMessage());
    } catch (GlobalException e) {
      logUtil.error(e.getCause(), e.getMessage());
    }
  }

  /**
   * 模板
   *
   * @param entity       数据实体
   * @param templateName 模板名称
   * @param cateLog      目录
   * @param suffix       后缀
   * @throws IOException       IO异常信息
   * @throws TemplateException 模板异常信息
   * @throws GlobalException   公共异常信息
   */
  private void template(GenerateEntity entity, String templateName, String cateLog, String suffix) throws IOException, TemplateException, GlobalException {
    String path = FileUtils.getPath(entity.getPackageName());
    // 生成 解析模板
    // 读取模板
    String html = FreemarkerUtil.analyticalTemplate(templateName, entity);
    html = html.replace("[begin]", "#{")
        .replace("[end]", "}")
        .replace("[this]", "this.");
    // 文件名
    String fileName = entity.getClassName() + suffix;
    // 保存文件
    FileUtils.saveFile(html, path, cateLog, fileName);
  }

  /**
   * 生成数据
   *
   * @param entity     解析数据
   * @param treeEntity 表结构数据
   * @param dataBase   数据库名
   * @param fields     属性
   */
  private void loadData(GenerateEntity entity, TreeEntity treeEntity, String dataBase, Field[] fields) {
    entity.setTable(treeEntity.getLabel());
    entity.setAuthor(HostUtils.getHostName());
    entity.setDataBase(dataBase);

    entity.setClassName(StringUtils.classFormat(treeEntity.getLabel()));
    entity.setClassLowName(StringUtils.attributeNameFormat(treeEntity.getLabel()));

    // 读取配置信息
    entity.setProjectName(ClassMetadataUtils.getAttributeString(EnableAutoGenerate.class, "projectName")
        .replace("[", "")
        .replace("]", ""));
    entity.setPackageName(ClassMetadataUtils.getAttributeString(EnableAutoGenerate.class, "basePackage")
        .replace("[", "")
        .replace("]", ""));
    entity.setDescription(treeEntity.getExplain());
    entity.setAuthor(HostUtils.getHostName());

    // 查询字段
    List<ChildrenEntity> childrenEntityList = new ArrayList<>();

    // 字段
    TableColumnEntity columnEntity = new TableColumnEntity();
    columnEntity.setTableName(treeEntity.getLabel());
    columnEntity.setTableSchema(dataBase);
    List<TableColumnEntity> columnEntityList = findTableColumnList(columnEntity);

    // 列
    for (TableColumnEntity column :
        columnEntityList) {
      // 验证基类是否有相同属性
      String columnName = StringUtils.attributeNameFormat(column.getColumnName());
      List list = Arrays.stream(fields)
          .filter(f -> columnName.equals(f.getName()))
          .collect(Collectors.toList());


      ChildrenEntity childrenEntity = new ChildrenEntity();
      if (list != null && list.size() > 0) {
        childrenEntity.setColumnShow(false);
      }

      childrenEntity.setColumnName(columnName);
      childrenEntity.setColumnDescription(column.getColumnComment());
      final String value = "fk";
      boolean keyIsShow = StringUtils.isNotNullAndEmpty(column.getColumnKey()) &&
          column.getColumnKey().toLowerCase().equals(value);
      childrenEntity.setColumnKey(keyIsShow);

      // 数据类型
      String dataType = column.getDataType();

      // 验证是否为空
      if (!StringUtils.isNullAndSpaceOrEmpty(dataType)) {
        // MyBatis类型
        MySqlJdbcTypeContrastEnums jdbcTypeContrastEnum;
        if (columnName.equals(dataType.toUpperCase())) {
          jdbcTypeContrastEnum = MySqlJdbcTypeContrastEnums.VARCHAR;
        } else {
          jdbcTypeContrastEnum = MySqlJdbcTypeContrastEnums
              .valueOf(dataType.toUpperCase());
        }
        childrenEntity.setColumnType(jdbcTypeContrastEnum.getCode());

        // Java 数据类型
        MySqlDataTypeContrastEnums contrastEnum;
        if (columnName.equals(dataType.toUpperCase())) {
          contrastEnum = MySqlDataTypeContrastEnums.VARCHAR;
        } else {
          contrastEnum = MySqlDataTypeContrastEnums
              .valueOf(dataType.toUpperCase());
        }
        childrenEntity.setDataType(contrastEnum.getValue());
        String code = contrastEnum.getCode();
        if (!entity.getLeading().contains(code) && StringUtils.isNotNullAndEmpty(code)) {
          entity.getLeading().add(code);
        }

        // 记录是否为生成扩展字段
        if (MySqlDataTypeContrastEnums.DATE.equals(contrastEnum) ||
            MySqlDataTypeContrastEnums.TIMESTAMP.equals(contrastEnum) ||
            MySqlDataTypeContrastEnums.DATETIME.equals(contrastEnum)) {
          columnExtension(childrenEntityList, column, contrastEnum);
        }
      }
      childrenEntity.setColumnName(StringUtils.attributeNameFormat(column.getColumnName()));
      childrenEntity.setFunColumnName(StringUtils.classFormat(column.getColumnName()));
      childrenEntity.setLength(StringUtils.isNullAndSpaceOrEmpty(column.getCharacterMaximumLength()) ? "-1" : column.getCharacterMaximumLength());
      childrenEntity.setNullable("YES".equals(column.getIsNullable()) ? "true" : "false");
      childrenEntity.setTableColumnName(column.getColumnName());
      // 添加子节点
      childrenEntityList.add(childrenEntity);
    }
    entity.setChildren(childrenEntityList);
  }

  /**
   * 扩展字段
   *
   * @param childrenEntityList 数据
   * @param column             字段名称
   * @param contrastEnum       数据类型
   */
  private void columnExtension(List<ChildrenEntity> childrenEntityList, TableColumnEntity column,
                               MySqlDataTypeContrastEnums contrastEnum) {
    // 扩展字段开始
    ChildrenEntity childrenEntity = new ChildrenEntity();
    childrenEntity.setIsExtension(1);
    childrenEntity.setColumnDescription(column.getColumnComment());
    childrenEntity.setColumnName(StringUtils.attributeNameFormat(column.getColumnName()) + "Start");
    childrenEntity.setDataType(contrastEnum.getValue());


    childrenEntityList.add(childrenEntity);
    // 扩展字段结束
    ChildrenEntity entity = new ChildrenEntity();
    entity.setIsExtension(1);
    entity.setColumnDescription(column.getColumnComment());
    entity.setColumnName(StringUtils.attributeNameFormat(column.getColumnName()) + "End");
    entity.setDataType(contrastEnum.getValue());

    // 添加子节点
    childrenEntityList.add(entity);
  }
}
