package com.cdkjframework.mybatispro.core.injector;

import com.cdkjframework.mybatispro.core.config.GlobalConfig;
import com.cdkjframework.mybatispro.core.injector.methods.*;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import com.cdkjframework.mybatispro.core.toolkit.GlobalConfigUtils;
import org.apache.ibatis.session.Configuration;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


/**
 * SQL 默认注入器
 *
 * @author hubin
 * @since 2018-04-10
 */
public class DefaultSqlInjector extends AbstractSqlInjector {

  @Override
  public List<AbstractMethod> getMethodList(Configuration configuration, Class<?> mapperClass, TableInfo tableInfo) {
    GlobalConfig.DbConfig dbConfig = GlobalConfigUtils.getDbConfig(configuration);
    Stream.Builder<AbstractMethod> builder = Stream.<AbstractMethod>builder()
        .add(new Insert(dbConfig.isInsertIgnoreAutoIncrementColumn()))
        .add(new Delete())
        .add(new Modify())
        .add(new SelectCount())
        .add(new SelectMaps())
        .add(new SelectObjs())
        .add(new SelectList());
    if (tableInfo.havePK()) {
      builder.add(new DeleteById())
          .add(new DeleteByIds())
          .add(new ModifyById())
          .add(new SelectById())
          .add(new SelectByIds());
    } else {
      logger.warn(String.format("%s ,Not found @TableId annotation, Cannot use Mybatis-Plus 'xxById' Method.",
          tableInfo.getEntityType()));
    }
    return builder.build().collect(toList());
  }
}
