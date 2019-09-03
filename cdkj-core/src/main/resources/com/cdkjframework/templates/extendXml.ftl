<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${className}Mapper">
    <resultMap id="baseExtendResultMap" type="${packageName}.entity.${className}Entity">
        <#list children as item>
            <#if (item.columnName=='id' && item.isExtension==0)>
            <id column="${item.tableColumnName}" property="${item.columnName}" jdbcType="${item.columnType}"/>
            <#elseif item.isExtension==0>
            <result column="${item.tableColumnName}" property="${item.columnName}" jdbcType="${item.columnType}"/>
            </#if>
        </#list>
    </resultMap>


    <sql id="base_Extend_Column_List">
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list children as item>
                <#if item.isExtension==0>
                    ${item.tableColumnName},
                </#if>
            </#list>
        </trim>
    </sql>
</mapper>