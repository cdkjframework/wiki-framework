<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${className}Mapper">
    <resultMap id="baseResultMap" type="${packageName}.entity.${className}Entity">
        <#list children as item>
            <#if item.columnKey>
            <id column="${item.tableColumnName}" property="${item.columnName}" jdbcType="${item.columnType}"/>
            <#else>
            <result column="${item.tableColumnName}" property="${item.columnName}" jdbcType="${item.columnType}"/>
            </#if>
        </#list>
    </resultMap>
    <sql id="base_Column_List">
        <#list children as item>
            ${item.tableColumnName},
        </#list>
        '' as emptyValue
    </sql>
    <update id="modify${className}" parameterType="${packageName}.entity.${className}Entity">
        UPDATE ${table}
        SET
        <trim prefix="" suffix="" suffixOverrides=",">
        <#list children as item>
            <#if !item.columnKey>
            <if test="${item.columnName} != null">
                ${item.tableColumnName} = [begin]${item.columnName},jdbcType=${item.columnType}[end],
            </if>
            </#if>
        </#list>
        </trim>
        WHERE
        <#list children as item>
            <#if item.columnKey>
                ${item.tableColumnName} = [begin]${item.columnName},jdbcType=${item.columnType}[end]
            </#if>
        </#list>
    </update>

    <insert id="add${className}" parameterType="${packageName}.entity.${className}Entity">
        INSERT INTO ${table}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list children as item>
            <if test="${item.columnName} != null">
                ${item.tableColumnName},
            </if>
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list children as item>
                <if test="${item.columnName} != null">
                    [begin]${item.columnName},jdbcType=${item.columnType}[end],
                </if>
            </#list>
        </trim>
    </insert>

    <delete id="delete${className}" parameterType="${packageName}.entity.${className}Entity">
        DELETE
        FROM
        ${table}
        WHERE
        <#list children as item>
            <#if item.columnKey>
                ${item.tableColumnName} = [begin]${item.columnName},jdbcType=${item.columnType}[end]
            </#if>
        </#list>
    </delete>

    <select id="find${className}PageList" parameterType="${packageName}.entity.${className}Entity" resultMap="baseResultMap">
        SELECT
        <include refid="base_Column_List"></include>
        FROM ${table}
        WHERE 1 = 1
        <#list children as item>
            <if test="${item.columnName} != null">
                AND ${item.tableColumnName} = [begin]${item.columnName},jdbcType=${item.columnType}[end]
            </if>
        </#list>
    </select>
</mapper>