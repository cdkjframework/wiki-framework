package com.cdkjframework.center.service.impl;

import com.cdkjframework.center.annotation.EnableAutoGenerate;
import com.cdkjframework.center.service.GenerateService;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.AutoGenerateConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.core.business.mapper.GenerateMapper;
import com.cdkjframework.entity.BaseEntity;
import com.cdkjframework.entity.generate.template.*;
import com.cdkjframework.entity.generate.template.children.ChildrenEntity;
import com.cdkjframework.enums.datasource.MySqlDataTypeContrastEnums;
import com.cdkjframework.enums.datasource.MySqlJdbcTypeContrastEnums;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.files.FileUtils;
import com.cdkjframework.util.files.freemarker.FreemarkerUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.CollectUtils;
import com.cdkjframework.util.tool.CopyUtils;
import com.cdkjframework.util.tool.HostUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.meta.ClassMetadataUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
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
public class GenerateServiceImpl implements GenerateService {

  /**
   * 存储库Int
   */
  private final String REPOSITORY_INT = "repositoryInt";

  /**
   * 环境
   */
  @Value("${spring.application.name}")
  private String active;

  /**
   * 配置
   */
  private final CustomConfig customConfig;

  /**
   * 日志
   */
  private LogUtils logUtil = LogUtils.getLogger(GenerateServiceImpl.class);

  /**
   * 生成 mapper
   */
  private final GenerateMapper generateMapper;

  /**
   * 获取实例
   */
  private FreemarkerUtils freemarker;

  /**
   * 构建函数
   */
  public GenerateServiceImpl(CustomConfig customConfig, GenerateMapper generateMapper) {
    this.customConfig = customConfig;
    this.generateMapper = generateMapper;
    freemarker = FreemarkerUtils.getInstance(customConfig);
  }

  /**
   * 获取数据库
   *
   * @return 返回结果
   */
  @Override
  public DatabaseEntity findDatabase() {
    if (StringUtils.isNullAndSpaceOrEmpty(active) && !DEFAULT_ACTIVE.equals(active.toLowerCase())) {
      return new DatabaseEntity();
    }

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
        /**
         * 替换值
         */
        String replacement = " ";
        treeColumn.setExplain(ConvertUtils.convertString(column.getColumnComment())
            .replace("\\n", replacement)
            .replace("\\t", replacement)
            .replace("\\s", replacement)
            .replace("\\r", replacement));

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
        if (optional.isEmpty()) {
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
      List<String> pathList = entity.getPath();
      boolean isXml = false;
      for (TemplateEntity temp :
          TEMPLATE_LIST) {
        StringBuffer path = new StringBuffer(entity.getBasePath());
        if (temp.getTemplateName().contains(JPA) && !entity.getJpa()) {
          continue;
        }
        if (MY_BATIS.contains(temp.getTemplateName()) && !entity.getMyBatis()) {
          continue;
        }
        if (StringUtils.isNotNullAndEmpty(path)) {
          switch (temp.getTemplateName()) {
            case "vo":
              path.append("entity/");
              path.append(pathList.get(IntegerConsts.ZERO));
              break;
            case "dto":
              path.append("entity/");
              path.append(pathList.get(IntegerConsts.ONE));
              break;
            case "entity":
            case "extend":
              path.append("entity/");
              path.append(pathList.get(IntegerConsts.TWO));
              break;
            case "controller":
              path.append(pathList.get(IntegerConsts.THREE));
              break;
            case "service":
            case "interface":
              path.append(pathList.get(IntegerConsts.FOUR));
              break;
            case "repository":
            case "repositoryInt":
              if (entity.getMyBatis()) {
                path.append(pathList.get(IntegerConsts.SEVEN));
              } else {
                path.append(pathList.get(IntegerConsts.FIVE));
              }
              break;
            case "mapper":
              path.append(pathList.get(IntegerConsts.FIVE));
              break;
            case "mapperXml":
            case "extendXml":
              path.append(pathList.get(IntegerConsts.SIX));
              isXml = true;
              break;
          }
          path.append("/src/main/");
          if (isXml) {
            path.append("resources/");
          } else {
            path.append("java/");
          }
        }
        if (!entity.isIntTemplate() && temp.getTemplateName().equals(REPOSITORY_INT)) {
          continue;
        }
        if (!isXml && path.length() > IntegerConsts.ZERO) {
          path.append(entity.getPackageName().replace(StringUtils.POINT, DIVISION));
        }
        template(entity, temp.getTemplateName(), path.toString(), temp.getCateLog(), temp.getSuffix());
      }
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
   * @param path         路径
   * @param cateLog      目录
   * @param suffix       后缀
   * @throws IOException       IO异常信息
   * @throws TemplateException 模板异常信息
   * @throws GlobalException   公共异常信息
   */
  private void template(GenerateEntity entity, String templateName, String path, String cateLog, String suffix) throws IOException, TemplateException, GlobalException {
    if (StringUtils.isNullAndSpaceOrEmpty(path)) {
      path = FileUtils.getPath(entity.getPackageName());
    }
    // 生成 解析模板
    // 读取模板
    String html = freemarker.analyticalTemplate(templateName, entity);
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
    entity.setUri(treeEntity.getLabel().replace(StringUtils.UNDERLINE, StringUtils.BACKSLASH));
    // 读取配置信息
    Map<String, Object> map = ClassMetadataUtils.getAttribute(EnableAutoGenerate.class);
    for (Map.Entry<String, Object> entry :
        map.entrySet()) {
      List<Object> value = (List<Object>) entry.getValue();
      LinkedList<Object> element = new LinkedList<>(Arrays.asList(value.get(IntegerConsts.ZERO)));
      if (StringUtils.isNullAndSpaceOrEmpty(entry.getValue())) {
        continue;
      }
      switch (entry.getKey()) {
        case AutoGenerateConsts
            .PROJECT_NAME:
          entity.setProjectName(ConvertUtils.convertString(element.element()));
          break;
        case AutoGenerateConsts.BASE_PACKAGE:
          entity.setPackageName(ConvertUtils.convertString(element.element()));
          break;
        case AutoGenerateConsts.BASE_PATH:
          entity.setBasePath(ConvertUtils.convertString(element.element()));
          break;
        case AutoGenerateConsts.PATH:
          List<String> pathList = Arrays.asList((String[]) element.element());
          entity.setPath(pathList);
          break;
        case AutoGenerateConsts.JPA:
          entity.setJpa(ConvertUtils.convertBoolean(element.element()));
          break;
        case AutoGenerateConsts.MY_BATIS:
          entity.setMyBatis(ConvertUtils.convertBoolean(element.element()));
          break;
      }
    }
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
      if (CollectUtils.isNotEmpty(list)) {
        childrenEntity.setColumnShow(Boolean.TRUE);
      }
      childrenEntity.setColumnName(columnName);
      if (StringUtils.isNotNullAndEmpty(column.getColumnComment())) {
        childrenEntity.setColumnDescription(column.getColumnComment());
      } else {
        childrenEntity.setColumnDescription(StringUtils.Empty);
      }
      final List<String> value = Arrays.asList("fk", "pri");
      boolean keyIsShow = StringUtils.isNotNullAndEmpty(column.getColumnKey()) &&
          value.contains(column.getColumnKey().toLowerCase());
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
        if (keyIsShow) {
          entity.setIntTemplate(contrastEnum.equals(MySqlDataTypeContrastEnums.BIGINT) ||
              contrastEnum.equals(MySqlDataTypeContrastEnums.INT) ||
              contrastEnum.equals(MySqlDataTypeContrastEnums.ID) ||
              contrastEnum.equals(MySqlDataTypeContrastEnums.INT2));
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
      childrenEntity.setLength(StringUtils.isNullAndSpaceOrEmpty(column.getCharacterMaximumLength()) ? StringUtils.NEGATIVE_ONE : column.getCharacterMaximumLength());
      String nullable = "YES";
      childrenEntity.setNullable(nullable.equals(column.getIsNullable()) ? String.valueOf(Boolean.TRUE) : String.valueOf(Boolean.FALSE));
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
    if (StringUtils.isNotNullAndEmpty(column.getColumnComment())) {
      childrenEntity.setColumnDescription(column.getColumnComment());
    } else {
      childrenEntity.setColumnDescription(StringUtils.Empty);
    }
    childrenEntity.setColumnName(StringUtils.attributeNameFormat(column.getColumnName()) + "Start");
    childrenEntity.setDataType(contrastEnum.getValue());


    childrenEntityList.add(childrenEntity);
    // 扩展字段结束
    ChildrenEntity entity = new ChildrenEntity();
    entity.setIsExtension(1);
    if (StringUtils.isNotNullAndEmpty(column.getColumnComment())) {
      entity.setColumnDescription(column.getColumnComment());
    } else {
      entity.setColumnDescription(StringUtils.Empty);
    }
    entity.setColumnName(StringUtils.attributeNameFormat(column.getColumnName()) + "End");
    entity.setDataType(contrastEnum.getValue());

    // 添加子节点
    childrenEntityList.add(entity);
  }
}
