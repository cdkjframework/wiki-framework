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
                t.${item.tableColumnName},
            </#if>
        </#list>
        '' as emptyValue
    </sql>
    <update id="modify${className}" parameterType="${packageName}.entity.${className}Entity">
        UPDATE ${table}
        SET
        <trim prefix="" suffix="" suffixOverrides=",">
        <#list children as item>
            <#if item.isExtension==0>
                <#if !item.columnKey>
            <if test="${item.columnName} != null ">
                ${item.tableColumnName} = [begin]${item.columnName},jdbcType=${item.columnType}[end],
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

    <insert id="add${className}" parameterType="${packageName}.entity.${className}Entity">
        INSERT INTO ${table}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list children as item>
            <#if item.isExtension==0>
                <if test="${item.columnName} != null <#if item.columnType=='VARCHAR'> and ${item.columnName} != '' </#if>">
                    ${item.tableColumnName},
                </if>
            </#if>
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list children as item>
                <#if item.isExtension==0>
                    <if test="${item.columnName} != null <#if item.columnType=='VARCHAR'> and ${item.columnName} != '' </#if>">
                        [begin]${item.columnName},jdbcType=${item.columnType}[end],
                    </if>
                </#if>
            </#list>
        </trim>
    </insert>

    <delete id="delete${className}" parameterType="${packageName}.entity.${className}Entity">
        DELETE
        FROM
        ${table}
        WHERE
        <#list children as item>
            <#if item.columnName=='id'>
                ${item.tableColumnName} = [begin]${item.columnName},jdbcType=${item.columnType}[end]
            </#if>
        </#list>
    </delete>

    <select id="find${className}PageList" parameterType="${packageName}.entity.${className}Entity" resultType="${packageName}.entity.${className}Entity">
        SELECT
        <include refid="base_Column_List"></include>
        FROM ${table} t
       <where>
        <#list children as item>
            <#if item.isExtension==0>
                <if test="${item.columnName} != null <#if item.columnType=='VARCHAR'> and ${item.columnName} != '' </#if>" >
                    AND t.${item.tableColumnName} = [begin]${item.columnName},jdbcType=${item.columnType}[end]
                </if>
            </#if>
        </#list>
       </where>
    </select>
    <select id="find${className}" parameterType="${packageName}.entity.${className}Entity" resultType="${packageName}.entity.${className}Entity">
        SELECT
        <include refid="base_Column_List"></include>
        FROM ${table} t
        <where>
        <#list children as item>
            <#if item.isExtension==0>
            <if test="${item.columnName} != null <#if item.columnType=='VARCHAR'> and ${item.columnName} != '' </#if>" >
                AND t.${item.tableColumnName} = [begin]${item.columnName},jdbcType=${item.columnType}[end]
            </if>
            </#if>
        </#list>
        </where>
    </select>
    <select id="find${className}ById"  resultType="${packageName}.entity.${className}Entity">
        SELECT
        <include refid="base_Column_List"></include>
        FROM ${table} t
        WHERE t.id=[begin]id[end]
    </select>
</mapper>