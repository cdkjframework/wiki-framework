package ${packageName}.service.impl;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.core.member.CurrentUser;
import com.cdkjframework.entity.BaseEntity;
import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.CopyUtils;

import ${packageName}.Vo.${className}Vo;
import ${packageName}.entity.${className}Entity;
import ${packageName}.mapper.${className}Mapper;
import ${packageName}.service.${className}Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.*;

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
@RequiredArgsConstructor
public class ${className}Controller implements ${className}Service {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(${className}ServiceImpl.class);

    /**
     * ${description} Service
     */
    private final ${className}Service ${classLowName}ServiceImpl;

    /**
     * 修改数据
     *
     * @param ${classLowName}Vo ${description} - 实体
     */
    public void modify${className}(${className}Vo ${classLowName}Vo) {
        ${className}Dto entity = CopyUtils.copyProperties(${classLowName}Vo, ${className}Dto.class);
        ${classLowName}Vo.setEditTime(LocalDateTime.now());
        ${classLowName}Vo.setEditUserId(CurrentUser.getUserId());
        ${classLowName}Vo.setEditUserName(CurrentUser.getUserName());
        ${className}Entity entity = CopyUtils.copyProperties(${classLowName}Vo, ${className}Entity.class);
        ${classLowName}Mapper.modify(entity);
    }

    /**
     * 添加数据
     *
     * @param ${classLowName}Vo ${description} - 实体
     */
    public void add${className}(${className}Vo ${classLowName}Vo) {
        ${classLowName}Vo.setId(GeneratedValueUtils.getUuidString());
        ${classLowName}Vo.setAddTime(LocalDateTime.now());
        ${classLowName}Vo.setAddUserId(CurrentUser.getUserId());
        ${classLowName}Vo.setAddUserName(CurrentUser.getUserName());
        ${classLowName}Vo.setDeleted(IntegerConsts.ZERO);
        ${className}Entity entity = CopyUtils.copyProperties(${classLowName}Vo, ${className}Entity.class);
        ${classLowName}Mapper.insert(entity);
    }
    /**
     * 删除数据
     *
     * @param ${classLowName}Vo ${description} - 实体
     */
    public void delete${className}(${className}Vo ${classLowName}Vo) {
        ${className}Entity entity = CopyUtils.copyProperties(${classLowName}Vo, ${className}Entity.class);
        ${classLowName}Mapper.delete(entity);
    }

    /**
     * 查询分页数据
     *
     * @param ${classLowName}Vo 查询实体
     * @return 返回分页数据实体
     */
    public PageEntity list${className}Page(${className}Vo ${classLowName}Vo) {

        //返回对象
        PageEntity pageEntity = new PageEntity();

        //分页查询角色信息
        Page page = PageHelper.startPage(${classLowName}Vo.getPageIndex(), ${classLowName}Vo.getPageSize());

        //对象转换 Vo -> entity
        ${className}Entity entity = CopyUtils.copyProperties(${classLowName}Vo, ${className}Entity.class);

        ${classLowName}Mapper.listFindByEntity(entity);

        pageEntity.setPageIndex(${classLowName}Vo.getPageIndex());
        pageEntity.setTotal(page.getTotal());
        pageEntity.setData(page.getResult());

        //返回分页数据
        return pageEntity;
    }
}
