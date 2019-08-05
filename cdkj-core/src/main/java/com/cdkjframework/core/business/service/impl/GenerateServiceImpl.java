package com.cdkjframework.core.business.service.impl;

import com.cdkjframework.core.annotation.EnableAutoGenerate;
import com.cdkjframework.core.business.mapper.GenerateMapper;
import com.cdkjframework.core.business.service.GenerateService;
import com.cdkjframework.entity.BaseEntity;
import com.cdkjframework.entity.generate.template.*;
import com.cdkjframework.entity.generate.template.children.ChildrenEntity;
import com.cdkjframework.enums.datasource.MySqlDataTypeContrastEnum;
import com.cdkjframework.enums.datasource.MySqlJdbcTypeContrastEnum;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.files.FileUtil;
import com.cdkjframework.util.files.freemarker.FreemarkerUtil;
import com.cdkjframework.util.log.LogUtil;
import com.cdkjframework.util.tool.HostUtil;
import com.cdkjframework.util.tool.StringUtil;
import com.cdkjframework.util.tool.meta.ClassMetadataUtil;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.core.business.service.impl
 * @ClassName: GenerateServiceImpl
 * @Description: 生成服务
 * @Author: xiaLin
 * @Version: 1.0
 */

@Service
public class GenerateServiceImpl implements GenerateService {

    /**
     * 日志
     */
    private LogUtil logUtil = LogUtil.getLogger(GenerateServiceImpl.class);

    /**
     * 生成 mapper
     */
    @Autowired
    private GenerateMapper generateMapper;

    /**
     * 获取数据库
     *
     * @return 返回结果
     */
    @Override
    public DatabaseEntity findDatabase() {
        //获取当前用户数据库
        List<DatabaseEntity> myDataBase = generateMapper.findDatabase();
        if (myDataBase == null || myDataBase.size() == 0) {
            return new DatabaseEntity();
        }

        //获取到数据
        DatabaseEntity entity = myDataBase.get(0);
        //获取到全部数据
        List<DatabaseEntity> entityList = generateMapper.findDatabaseList();
        if (entityList != null) {
            entity.getChildren().addAll(entityList);
        }

        //返回结果
        return entity;
    }

    /**
     * 获取数据库表
     *
     * @param tableEntity 查询实体
     * @return 返回结果
     */
    @Override
    public List<TreeEntity> findDatabaseTableList(TableEntity tableEntity) {
        List<TableEntity> tableEntityList = generateMapper.findDatabaseTableList(tableEntity);
        List<TreeEntity> treeEntityList = new ArrayList<>();
        for (TableEntity entity :
                tableEntityList) {
            TreeEntity tree = new TreeEntity();
            tree.setId(entity.getTableName());
            tree.setLabel(entity.getTableName());
            tree.setExplain(entity.getTableComment());

            //字段
            TableColumnEntity columnEntity = new TableColumnEntity();
            columnEntity.setTableName(entity.getTableName());
            columnEntity.setTableSchema(entity.getTableSchema());
            List<TableColumnEntity> columnEntityList = findTableColumnList(columnEntity);
            for (TableColumnEntity column :
                    columnEntityList) {
                TreeEntity treeColumn = new TreeEntity();
                treeColumn.setId(column.getColumnName());
                treeColumn.setLabel(column.getColumnName());
                treeColumn.setExplain(column.getColumnComment());

                //添加子节点
                tree.getChildren().add(treeColumn);
            }

            //添加节点
            treeEntityList.add(tree);
        }

        //返回结果
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
        return generateMapper.findTableColumnList(columnEntity);
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
            //获取基础类里的属性
            Field[] fields = BaseEntity.class.getDeclaredFields();
            for (TreeEntity entity :
                    entityList) {
                Optional<TreeEntity> optional = treeEntityList
                        .stream()
                        .filter(f -> f.getLabel().equals(entity.getLabel()))
                        .findFirst();
                if (!optional.isPresent()) {
                    continue;
                }

                //找到表
                TreeEntity treeEntity = optional.get();

                //模板生成
                templateGeneration(treeEntity, dataBase, fields);
            }
            isGenerate = true;
        } catch (Exception ex) {
            logUtil.error(ex);
        }

        //返回结果
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

            //生成 entity
            template(entity, "entity", "\\entity\\", "Entity.java");

            //生成 dto
            template(entity, "dto", "\\dto\\", "Dto.java");
            //生成 vo
            template(entity, "vo", "\\vo\\", "Vo.java");

            //生成 service
            template(entity, "service", "\\service\\impl\\", "ServiceImpl.java");

            //生成 service Interface
            template(entity, "interface", "\\service\\", "Service.java");

            //生成 mapper java
            template(entity, "mapper", "\\mapper\\", "Mapper.java");

            //生成 mapper xml
            template(entity, "mapperXml", "\\mapper\\xml\\", "Mapper.xml");
        } catch (IOException e) {
            logUtil.error(e);
        } catch (TemplateException e) {
            logUtil.error(e);
        } catch (GlobalException e) {
            logUtil.error(e);
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
        String path = FileUtil.getPath(entity.getPackageName());
        //生成 解析模板
        String html = FreemarkerUtil.analyticalTemplate(templateName, entity);
        html = html.replace("[begin]", "#{")
                .replace("[end]", "}")
                .replace("[this]", "this.");
        //文件名
        String fileName = entity.getClassName() + suffix;
        //保存文件
        FileUtil.saveFile(html, path, cateLog, fileName);
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
        entity.setAuthor(HostUtil.getHostName());
        entity.setDataBase(dataBase);

        entity.setClassName(StringUtil.classFormat(treeEntity.getLabel()));
        entity.setClassLowName(StringUtil.attributeNameFormat(treeEntity.getLabel()));

        //读取配置信息
        entity.setProjectName(ClassMetadataUtil.getAttributeString(EnableAutoGenerate.class, "projectName")
                .replace("[", "")
                .replace("]", ""));
        entity.setPackageName(ClassMetadataUtil.getAttributeString(EnableAutoGenerate.class, "basePackage")
                .replace("[", "")
                .replace("]", ""));
        entity.setDescription(treeEntity.getExplain());
        entity.setAuthor(HostUtil.getHostName());

        //查询字段
        List<ChildrenEntity> childrenEntityList = new ArrayList<>();

        //字段
        TableColumnEntity columnEntity = new TableColumnEntity();
        columnEntity.setTableName(treeEntity.getLabel());
        columnEntity.setTableSchema(dataBase);
        List<TableColumnEntity> columnEntityList = findTableColumnList(columnEntity);

        //列
        for (TableColumnEntity column :
                columnEntityList) {
            //验证基类是否有相同属性
            String columnName = StringUtil.attributeNameFormat(column.getColumnName());
            List list = Arrays.stream(fields)
                    .filter(f -> columnName.equals(f.getName()))
                    .collect(Collectors.toList());


            ChildrenEntity childrenEntity = new ChildrenEntity();
            if (list != null && list.size() > 0) {
                childrenEntity.setColumnShow(false);
            }

            childrenEntity.setColumnName(columnName);
            childrenEntity.setColumnDescription(column.getColumnComment());
            childrenEntity.setColumnKey(StringUtil.isNotNullAndEmpty(column.getColumnKey()));

            //数据类型
            String dataType = column.getDataType();

            //验证是否为空
            if (!StringUtil.isNullAndSpaceOrEmpty(dataType)) {
                //MyBatis类型
                MySqlJdbcTypeContrastEnum jdbcTypeContrastEnum = MySqlJdbcTypeContrastEnum.valueOf(dataType.toUpperCase());
                childrenEntity.setColumnType(jdbcTypeContrastEnum.getCode());

                //Java 数据类型
                MySqlDataTypeContrastEnum contrastEnum = MySqlDataTypeContrastEnum.valueOf(dataType.toUpperCase());
                childrenEntity.setDataType(contrastEnum.getValue());
                String code = contrastEnum.getCode();
                if (!entity.getLeading().contains(code) && StringUtil.isNotNullAndEmpty(code)) {
                    entity.getLeading().add(code);
                }
            }
            childrenEntity.setColumnName(StringUtil.attributeNameFormat(column.getColumnName()));
            childrenEntity.setFunColumnName(StringUtil.classFormat(column.getColumnName()));
            childrenEntity.setLength(StringUtil.isNullAndSpaceOrEmpty(column.getCharacterMaximumLength()) ? "-1" : column.getCharacterMaximumLength());
            childrenEntity.setNullable("YES".equals(column.getIsNullable()) ? "true" : "false");
            childrenEntity.setTableColumnName(column.getColumnName());
            //添加子节点
            childrenEntityList.add(childrenEntity);
        }
        entity.setChildren(childrenEntityList);
    }
}