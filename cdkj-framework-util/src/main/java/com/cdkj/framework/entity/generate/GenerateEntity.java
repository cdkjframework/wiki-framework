package com.cdkj.framework.entity.generate;


import com.cdkj.framework.entity.generate.entity.ChildrenEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.entity.generate
 * @ClassName: GenerateBaseEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

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

    public List<String> getLeading() {
        return leading;
    }

    public void setLeading(List<String> leading) {
        this.leading = leading;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassLowName() {
        return classLowName;
    }

    public void setClassLowName(String classLowName) {
        this.classLowName = classLowName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setSerialVersionUID(String serialVersionUID) {
        this.serialVersionUID = serialVersionUID;
    }

    public List<ChildrenEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenEntity> children) {
        this.children = children;
    }
}
