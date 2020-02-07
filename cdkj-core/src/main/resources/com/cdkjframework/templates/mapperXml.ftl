<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${className}Mapper">
    <resultMap id="baseResultMap" type="${packageName}.entity.${className}Entity">
        <#list children as item>
            <#if (item.columnName=='id' && item.isExtension==0)>
                <id column="${item.tableColumnName}" property="${item.columnName}" jdbcType="${item.columnType}"/>
            <#elseif item.isExtension==0>
                <result column="${item.tableColumnName}" property="${item.columnName}" jdbcType="${item.columnType}"/>
            </#if>
        </#list>
    </resultMap>


    <sql id="base_Column_List">
    <#list children as item>
        <#if item.isExtension==0>
            ${item.tableColumnName}<#if item_has_next >,</#if>
        </#if>
    </#list>
    </sql>


    <insert id="insert" parameterType="${packageName}.entity.${className}Entity">
        INSERT INTO ${table}
        (<include refid="base_Column_List"></include>)
        VALUES
        (
        <#list children as item>
            <#if item.isExtension==0>
                [begin]${item.columnName},jdbcType=${item.columnType}[end]<#if item_has_next >,</#if>
            </#if>
        </#list>
        )
    </insert>

    <insert id="insertBatch" parameterType="java.util.List">
        INSERT INTO ${table}
        (<include refid="base_Column_List"></include>)
        VALUES
        <foreach collection="list" item="item" index="index" separator=",">
        (<#list children as item>
            <#if item.isExtension==0>
                <#if !item.columnKey>
                    [begin]${item.columnName},jdbcType=${item.columnType}[end]<#if item_has_next >,</#if>
                </#if>
            </#if>
        </#list>)
        </foreach>
    </insert>


    <delete id="delete" parameterType="${packageName}.entity.${className}Entity">
        DELETE
        FROM
        ${table}
        <where>
        <#list children as item>
            <#if item.isExtension==0>
                <if test="${item.columnName} != null ">
                    AND ${item.tableColumnName} = [begin]${item.columnName},jdbcType=${item.columnType}[end]
                </if>
            </#if>
        </#list>
        </where>
    </delete>

    <delete id="deleteById">
        DELETE
        FROM
        ${table}
        WHERE id = [begin]id[end]
    </delete>

    <delete id="deleteBatchIds">
        DELETE
        FROM
        ${table}
        WHERE id IN
        <foreach collection="coll" item="item" index="index" open="(" close=")" separator=",">
            [begin]item[end]
        </foreach>
    </delete>


    <update id="modify" parameterType="${packageName}.entity.${className}Entity">
        UPDATE ${table}
        SET
        <trim suffixOverrides=",">
        <#list children as item>
            <#if item.isExtension==0>
                <#if !item.columnKey>
                    <if test="${item.columnName} != null ">
                        ${item.tableColumnName} = [begin]${item.columnName},jdbcType=${item.columnType}[end]<#if item_has_next >,</#if>
                    </if>
                </#if>
            </#if>
        </#list>
        </trim>
        WHERE
        <#list children as item>
            <#if item.columnName=='id'>
                ${item.tableColumnName} = [begin]${item.columnName},jdbcType=${item.columnType}[end]
            </#if>
        </#list>
    </update>

    <update id="modifyBatch" parameterType="java.util.List">
        <foreach collection="list" item="item" index="index" separator=";">
            UPDATE ${table}
            <set>
            <#list children as item>
                <#if item.isExtension==0>
                    <#if !item.columnKey>
                        <if test="${item.columnName} != null ">
                            ${item.tableColumnName} = [begin]item.${item.columnName},jdbcType=${item.columnType}[end]<#if item_has_next >,</#if>
                        </if>
                    </#if>
                </#if>
            </#list>
            </set>
            WHERE
            <#list children as item>
                <#if item.columnName=='id'>
                    ${item.tableColumnName} = [begin]item.${item.columnName},jdbcType=${item.columnType}[end]
                </#if>
            </#list>
        </foreach>
    </update>


    <select id="findEntityById" resultMap="baseResultMap">
        SELECT
        <include refid="base_Column_List"></include>
        FROM ${table}
        WHERE id=[begin]id[end]
    </select>

    <select id="listFindByIds" resultMap="baseResultMap">
        SELECT
        <include refid="base_Column_List"></include>
        FROM ${table}
        WHERE id IN
        <foreach collection="col" item="item" index="index" open="(" close=")" separator=",">
            [begin]item[end]
        </foreach>
    </select>

    <select id="findEntity" parameterType="${packageName}.entity.${className}Entity" resultMap="baseResultMap">
        SELECT
        <include refid="base_Column_List"></include>
        FROM ${table}
        <where>
            <#list children as item>
                <#if item.isExtension==0>
                    <if test="${item.columnName} != null <#if item.columnType=='VARCHAR'> and ${item.columnName} != '' </#if>" >
                        AND ${item.tableColumnName} = [begin]${item.columnName},jdbcType=${item.columnType}[end]
                    </if>
                </#if>
            </#list>
        </where>
    </select>

    <select id="listFindByEntity" parameterType="${packageName}.entity.${className}Entity" resultMap="baseResultMap">
        SELECT
        <include refid="base_Column_List"></include>
        FROM ${table}
        <where>
            <#list children as item>
                <#if item.isExtension==0>
                    <if test="${item.columnName} != null <#if item.columnType=='VARCHAR'> and ${item.columnName} != '' </#if>" >
                        AND ${item.tableColumnName} = [begin]${item.columnName},jdbcType=${item.columnType}[end]
                    </if>
                </#if>
            </#list>
        </where>
    </select>
</mapper>
