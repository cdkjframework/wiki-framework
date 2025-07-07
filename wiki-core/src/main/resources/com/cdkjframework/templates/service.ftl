package ${packageName}.service.impl;

import com.framewiki.constant.IntegerConsts;
import com.framewiki.core.member.CurrentUser;
import com.cdkjframework.entity.BaseEntity;
import com.cdkjframework.entity.PageEntity;
import com.framewiki.util.log.LogUtils;
import com.framewiki.util.make.GeneratedValueUtils;
import com.framewiki.util.tool.CopyUtils;

import ${packageName}.dto.${className}Dto;
import ${packageName}.entity.${className}Entity;

<#if myBatis>
import ${packageName}.mapper.${className}Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
</#if>
<#if jpa>
import com.framewiki.datasource.jpa.builder.JpaCriteriaBuilder;
import ${packageName}.repository.${className}Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import java.util.Optional;
</#if>
import ${packageName}.service.${className}Service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ProjectName: ${projectName}
 * @Package: ${packageName}
 * @ClassName: ${className}ServiceImpl
 * @Description: ${description}
 * @Author: ${author}
 * @Version: 1.0
 */

@Service
@RequiredArgsConstructor
public class ${className}ServiceImpl implements ${className}Service {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(${className}ServiceImpl.class);

    <#if myBatis>
        /**
        * ${description} mapper
        */
        private final ${className}Mapper ${classLowName}Mapper;
    </#if>
    <#if jpa>
        /**
        * ${description} repository
        */
        private final ${className}Repository ${classLowName}Repository;
    </#if>

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

    <#if myBatis && !jpa>
        ${classLowName}Mapper.modify(entity);
    </#if>
    <#if jpa>
        ${classLowName}Repository.save(entity);
    </#if>
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
        ${classLowName}Dto.setStatus(IntegerConsts.ONE);
        ${className}Entity entity = CopyUtils.copyProperties(${classLowName}Dto, ${className}Entity.class);
        <#if myBatis && !jpa>
            ${classLowName}Mapper.insert(entity);
        </#if>
        <#if jpa>
            ${classLowName}Repository.save(entity);
        </#if>
    }
    /**
     * 删除数据
     *
     * @param ${classLowName}Dto ${description} - 实体
     */
    @Override
    public void delete${className}(${className}Dto ${classLowName}Dto) {
        ${className}Entity entity = CopyUtils.copyProperties(${classLowName}Dto, ${className}Entity.class);
    <#if myBatis && !jpa>
        ${classLowName}Mapper.delete(entity);
    </#if>
    <#if jpa>
        ${classLowName}Repository.delete(entity);
    </#if>
    }

    /**
    * 查询数据
    *
    * @param ${classLowName}Dto ${description} - 实体
    * @return 返回结果
    */
    @Override
    public ${className}Dto find${className}(${className}Dto ${classLowName}Dto){
        <#if myBatis && !jpa>
        ${className}Entity entity = CopyUtils.copyProperties(${classLowName}Dto, ${className}Entity.class);
        entity = ${classLowName}Mapper.findEntity(entity);
        return CopyUtils.copyProperties(entity, ${className}Dto.class);
        </#if>
        <#if jpa>
        JpaCriteriaBuilder<${className}Entity> builder = JpaCriteriaBuilder.Builder();
        builder = builder.autoBuilder(builder, ${classLowName}Dto, ${className}Entity.class);
        Optional<${className}Entity> optional = ${classLowName}Repository.findOne(builder.build());
        return optional.map(${classLowName} -> CopyUtils.copyProperties(${classLowName}, ${classLowName}Dto.class)).orElse(null);
        </#if>
    }

    /**
    * 添加数据
    *
    * @param id 查询条件
    * @return 返回结果
    */
    @Override
    public ${className}Dto find${className}ById(String id){
        <#if myBatis && !jpa>
        ${className}Entity entity = ${classLowName}Mapper.findEntityById(id);
        return CopyUtils.copyProperties(entity, ${className}Dto.class);
        </#if>
        <#if jpa>
        Optional<${className}Entity> optional = ${classLowName}Repository.findById(id);
        return optional.map(${classLowName} -> CopyUtils.copyProperties(${classLowName}, ${classLowName}Dto.class)).orElse(null);
        </#if>
    }

    /**
     * 查询分页数据
     *
     * @param ${classLowName}Dto 查询实体
     * @return 返回分页数据实体
     */
    @Override
    public PageEntity<${className}Dto> list${className}Page(${className}Dto ${classLowName}Dto) {
        //分页查询${description}信息
        <#if myBatis && !jpa>
        Page page = PageHelper.startPage(${classLowName}Dto.getPageIndex(), ${classLowName}Dto.getPageSize());
        //对象转换 dto -> entity
        ${className}Entity entity = CopyUtils.copyProperties(${classLowName}Dto, ${className}Entity.class);

        ${classLowName}Mapper.listFindByEntity(entity);

        return PageEntity.build(${classLowName}Dto.getPageIndex(), page.getTotal(), page.getResult());
        </#if>
        <#if jpa>
        JpaCriteriaBuilder<${className}Entity> builder = JpaCriteriaBuilder.Builder();
        builder = builder.autoBuilder(builder, ${classLowName}Dto, ${className}Entity.class);

        // 增加排序
        builder = builder.orderBy(${className}Dto.ADD_TIME, Sort.Direction.DESC);

        // 增加分页
        int pageIndex = ${classLowName}Dto.getPageIndex() - IntegerConsts.ONE;
        builder = builder.page(pageIndex)
        .size(${classLowName}Dto.getPageSize());

        // 查询数据
        Page<${className}Entity> page = ${classLowName}Repository.findAll(builder.build(), builder.toPageable());

        // 将 Entity 转换为 Dto
        List<${className}Dto> ${classLowName}List = CopyUtils.copyProperties(page.getContent(), ${className}Dto.class);

        // 返回分页数据
        return PageEntity.build(${classLowName}Dto.getPageIndex(), page.getTotalElements(), ${classLowName}List);
        </#if>
    }

    /**
    * 查询列表数据
    *
    * @param ids 查询条件
    * @return 返回列表数据实体
    */
    @Override
    public List<${className}Dto> list${className}ByIds(List<String> ids){
        <#if myBatis && !jpa>
        List<${className}Entity> entitys = ${classLowName}Mapper.listFindByIds(ids);
        </#if>
        <#if jpa>
        List<${className}Entity> entitys = ${classLowName}Repository.findAllById(ids);
        </#if>
        //返回数据
        return CopyUtils.copyProperties(entitys, ${className}Dto.class);
    }

    /**
    * 查询列表数据
    *
    * @param ${classLowName}Dto 查询实体
    * @return 返回列表数据实体
    */
    @Override
    public List<${className}Dto> list${className}(${className}Dto ${classLowName}Dto){
        <#if myBatis && !jpa>
        //对象转换 dto -> entity
        ${className}Entity entity = CopyUtils.copyProperties(${classLowName}Dto, ${className}Entity.class);
        List<${className}Entity> entitys = ${classLowName}Mapper.listFindByEntity(entity);
        </#if>
        <#if jpa>
        Specification<${className}Entity> specification = buildSpecification(${classLowName}Dto);
        List<${className}Entity> entitys = ${classLowName}Repository.findAll(specification);
        </#if>

        //返回数据
        return CopyUtils.copyProperties(entitys, ${className}Dto.class);
    }

    <#if jpa>
    /**
    * 构建查询条件
    *
    * @param ${classLowName}Dto 查询实体
    * @return 返回结果
    */
    private Specification<${className}Entity> buildSpecification(${className}Dto ${classLowName}Dto) {
        return new Specification<${className}Entity>() {
            @Override
            public Predicate toPredicate(Root<${className}Entity> root, CriteriaQuery<?> criteriaQuery,
                                                            CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
    }
    </#if>
}
