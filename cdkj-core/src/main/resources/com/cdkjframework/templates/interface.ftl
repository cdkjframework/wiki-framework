package ${packageName}.service;

import com.cdkjframework.core.base.service.BasicService;
import com.cdkjframework.entity.PageEntity;

import ${packageName}.dto.${className}Dto;
import ${packageName}.entity.${className}Entity;

import javax.persistence.*;

/**
 * @ProjectName: ${projectName}
 * @Package: ${packageName}
 * @ClassName: ${className}Service
 * @Description: ${description}
 * @Author: ${author}
 * @Version: 1.0
 */

public interface ${className}Service extends BasicService {

    /**
     * 修改数据
     *
     * @param ${classLowName}Dto ${description} - 实体
     */
    void modify${className}(${className}Dto ${classLowName}Dto);

    /**
     * 添加数据
     *
     * @param ${classLowName}Dto ${description} - 实体
     */
    void add${className}(${className}Dto ${classLowName}Dto);

    /**
     * 删除数据
     *
     * @param ${classLowName}Dto ${description} - 实体
     */
    void delete${className}(${className}Dto ${classLowName}Dto);

    /**
     * 查询分页数据
     *
     * @param ${classLowName}Dto 查询实体
     * @return 返回分页数据实体
     */
    PageEntity<${className}Dto> list${className}Page(${className}Dto ${classLowName}Dto);
}
