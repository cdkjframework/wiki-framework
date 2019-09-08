package ${packageName}.mapper;

import com.cdkjframework.core.base.mapper.BaseMapper;
import ${packageName}.entity.${className}Entity;

import org.springframework.stereotype.Component;

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

@Component
public interface ${className}Mapper extends BaseMapper<${className}Entity> {

}
