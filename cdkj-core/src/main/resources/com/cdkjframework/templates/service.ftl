package ${packageName}.service.impl;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.core.member.CurrentUser;
import com.cdkjframework.entity.BaseEntity;
import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.CopyUtils;

import ${packageName}.dto.${className}Dto;
import ${packageName}.entity.${className}Entity;
import ${packageName}.mapper.${className}Mapper;
import ${packageName}.service.${className}Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @ProjectName: ${projectName}
 * @Package: ${packageName}
 * @ClassName: ${className}
 * @Description: ${description}
 * @Author: ${author}
 * @Version: 1.0
 */

@Service
public class ${className}ServiceImpl implements ${className}Service {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(${className}ServiceImpl.class);

    /**
     * ${description} mapper
     */
    @Autowired
    private final ${className}Mapper ${classLowName}Mapper;

    /**
    * 构造函数
    */
    public ${className}ServiceImpl(${className}Mapper ${classLowName}Mapper){
        this.${classLowName}Mapper = ${classLowName}Mapper
    }

    /**
     * 修改数据
     *
     * @param ${classLowName}Dto ${description} - 实体
     */
    @Override
    public void modify${className}(${className}Dto ${classLowName}Dto) {
        ${classLowName}Dto.setEditTime(LocalDateTime.now());
        ${classLowName}Dto.setEditUserId(CurrentUser.getUserId());
        ${classLowName}Dto.setEditUserName(CurrentUser.getUserName());
        ${className}Entity entity = CopyUtils.copyProperties(${classLowName}Dto, ${className}Entity.class);
        ${classLowName}Mapper.modify(entity);
    }

    /**
     * 添加数据
     *
     * @param ${classLowName}Dto ${description} - 实体
     */
    @Override
    public void add${className}(${className}Dto ${classLowName}Dto) {
        ${classLowName}Dto.setId(GeneratedValueUtils.getUuidString());
        ${classLowName}Dto.setAddTime(LocalDateTime.now());
        ${classLowName}Dto.setAddUserId(CurrentUser.getUserId());
        ${classLowName}Dto.setAddUserName(CurrentUser.getUserName());
        ${classLowName}Dto.setDeleted(IntegerConsts.ZERO);
        ${className}Entity entity = CopyUtils.copyProperties(${classLowName}Dto, ${className}Entity.class);
        ${classLowName}Mapper.insert(entity);
    }
    /**
     * 删除数据
     *
     * @param ${classLowName}Dto ${description} - 实体
     */
    @Override
    public void delete${className}(${className}Dto ${classLowName}Dto) {
        ${className}Entity entity = CopyUtils.copyProperties(${classLowName}Dto, ${className}Entity.class);
        ${classLowName}Mapper.delete(entity);
    }

    /**
     * 查询分页数据
     *
     * @param ${classLowName}Dto 查询实体
     * @return 返回分页数据实体
     */
    @Override
    public PageEntity list${className}Page(${className}Dto ${classLowName}Dto) {

        //返回对象
        PageEntity pageEntity = new PageEntity();

        //分页查询角色信息
        Page page = PageHelper.startPage(${classLowName}Dto.getPageIndex(), ${classLowName}Dto.getPageSize());

        //对象转换 dto -> entity
        ${className}Entity entity = CopyUtils.copyProperties(${classLowName}Dto, ${className}Entity.class);

        ${classLowName}Mapper.listFindByEntity(entity);

        pageEntity.setPageIndex(${classLowName}Dto.getPageIndex());
        pageEntity.setTotal(page.getTotal());
        pageEntity.setData(page.getResult());

        //返回分页数据
        return pageEntity;
    }
}
