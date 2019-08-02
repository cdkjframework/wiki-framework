package ${packageName}.mapper;

import ${packageName}.entity.${className}Entity;

import javax.persistence.*;
import java.util.List;

/**
 * @ProjectName: ${projectName}
 * @Package: ${packageName}
 * @ClassName: ${className}
 * @Description: ${description}
 * @Author: ${author}
 * @Version: 1.0
 */

public interface ${className}Mapper {

    /**
     * 修改数据
     *
     * @param ${classLowName}Entity ${description} - 实体
     */
    void modify${className}(${className}Entity ${classLowName}Entity);

    /**
     * 添加数据
     *
     * @param ${classLowName}Entity ${description} - 实体
     */
    void add${className}(${className}Entity ${classLowName}Entity);

    /**
     * 删除数据
     *
     * @param ${classLowName}Entity ${description} - 实体
     */
    void delete${className}(${className}Entity ${classLowName}Entity);

    /**
     * 查询批量数据
     *
     * @param ${classLowName}Entity 查询实体
     * @return 返回数据实体
     */
    List<${className}Entity> find${className}PageList(${className}Entity ${classLowName}Entity);
    /**
     * 查询单个数据
     *
     * @param ${classLowName}Entity 查询实体
     * @return 返回数据实体
     */
    ${className}Entity find${className}(${className}Entity ${classLowName}Entity);
    ${className}Entity find${className}ById(String id);

}
