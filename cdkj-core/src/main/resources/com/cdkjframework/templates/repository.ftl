package ${packageName}.repository;

import com.cdkjframework.datasource.jpa.repository.IRepositoryString;
import com.cdkjframework.entity.PageEntity;

import ${packageName}.dto.${className}Dto;
import ${packageName}.entity.${className}Entity;

import javax.persistence.*;

import java.util.List;
/**
 * @ProjectName: ${projectName}
 * @Package: ${packageName}
 * @ClassName: ${className}Repository
 * @Description: ${description}
 * @Author: ${author}
 * @Version: 1.0
 */

public interface ${className}Repository extends IRepositoryString<${className}Entity> {
}
