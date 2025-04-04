package com.cdkjframework.mybatispro.core.mapper;

import com.cdkjframework.mybatispro.core.injector.InjectorResolver;
import com.cdkjframework.mybatispro.core.MybatisMethodResolver;
import com.cdkjframework.mybatispro.core.metadata.IPage;
import com.cdkjframework.mybatispro.core.plugins.IgnoreStrategy;
import com.cdkjframework.mybatispro.core.plugins.InterceptorIgnoreHelper;
import com.cdkjframework.mybatispro.core.toolkit.GlobalConfigUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.CacheRefResolver;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.parsing.PropertyParser;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.mapper
 * @ClassName: MybatisMapperAnnotationBuilder
 * @Description: Mybatis映射器注释生成器
 * @Author: xiaLin
 * @Version: 1.0
 */
public class MybatisMapperAnnotationBuilder extends MapperAnnotationBuilder {

  /**
   * 声明注释类型
   */
  private static final Set<Class<? extends Annotation>> statementAnnotationTypes = Stream
      .of(Select.class, Update.class, Insert.class, Delete.class, SelectProvider.class, UpdateProvider.class,
          InsertProvider.class, DeleteProvider.class)
      .collect(Collectors.toSet());

  /**
   * 配置
   */
  private final Configuration configuration;

  /**
   * 助手
   */
  private final MybatisMapperBuilderAssistant assistant;

  /**
   * 类型
   */
  private final Class<?> type;

  /**
   * 构造方法
   *
   * @param configuration 配置
   * @param type          类型
   */
  public MybatisMapperAnnotationBuilder(Configuration configuration, Class<?> type) {
    super(configuration, type);
    String resource = type.getName().replace(StringUtils.DOT, StringUtils.SLASH) + ".java (best guess)";
    this.assistant = new MybatisMapperBuilderAssistant(configuration, resource);
    this.configuration = configuration;
    this.type = type;
  }

  /**
   * 解析
   */
  @Override
  public void parse() {
    String resource = type.toString();
    if (!configuration.isResourceLoaded(resource)) {
      loadXmlResource();
      configuration.addLoadedResource(resource);
      String mapperName = type.getName();
      assistant.setCurrentNamespace(mapperName);
      parseCache();
      parseCacheRef();
      IgnoreStrategy ignoreStrategy = InterceptorIgnoreHelper.initSqlParserInfoCache(type);
      for (Method method : type.getMethods()) {
        InterceptorIgnoreHelper.initSqlParserInfoCache(ignoreStrategy, mapperName, method);
        if (!canHaveStatement(method)) {
          continue;
        }
        if (getAnnotationWrapper(method, false, Select.class, SelectProvider.class).isPresent()
            && method.getAnnotation(ResultMap.class) == null) {
          parseResultMap(method);
        }
        try {
          parseStatement(method);
        } catch (IncompleteElementException e) {
          configuration.addIncompleteMethod(new MybatisMethodResolver(this, method));
        }
      }
      try {
        if (GlobalConfigUtils.isSupperMapperChildren(configuration, type)) {
          parserInjector();
        }
      } catch (IncompleteElementException e) {
        configuration.addIncompleteMethod(new InjectorResolver(this));
      }
    }
    configuration.parsePendingMethods(false);
  }

  /**
   * 解析注入器
   */
  public void parserInjector() {
    GlobalConfigUtils.getSqlInjector(configuration).inspectInject(assistant, type);
  }

  /**
   * 是否可以有语句
   *
   * @param method 方法
   * @return boolean
   */
  private static boolean canHaveStatement(Method method) {
    return !method.isBridge() && !method.isDefault();
  }

  /**
   * 加载XML资源
   */
  private void loadXmlResource() {
    if (!configuration.isResourceLoaded("namespace:" + type.getName())) {
      String xmlResource = type.getName().replace(StringUtils.DOT, StringUtils.SLASH) + ".xml";
      InputStream inputStream = type.getResourceAsStream(StringUtils.SLASH + xmlResource);
      if (inputStream == null) {
        try {
          inputStream = Resources.getResourceAsStream(type.getClassLoader(), xmlResource);
        } catch (IOException e2) {
          // ignore, resource is not required
        }
      }
      if (inputStream != null) {
        MybatisXMLMapperBuilder xmlParser = new MybatisXMLMapperBuilder(inputStream, assistant.getConfiguration(), xmlResource, configuration.getSqlFragments(), type.getName());
        xmlParser.parse();
      }
    }
  }

  /**
   * 解析缓存
   */
  private void parseCache() {
    CacheNamespace cacheDomain = type.getAnnotation(CacheNamespace.class);
    if (cacheDomain != null) {
      Integer size = cacheDomain.size() == 0 ? null : cacheDomain.size();
      Long flushInterval = cacheDomain.flushInterval() == 0 ? null : cacheDomain.flushInterval();
      Properties props = convertToProperties(cacheDomain.properties());
      assistant.useNewCache(cacheDomain.implementation(), cacheDomain.eviction(), flushInterval, size,
          cacheDomain.readWrite(), cacheDomain.blocking(), props);
    }
  }

  /**
   * 转换属性
   *
   * @param properties 属性
   * @return 属性
   */
  private Properties convertToProperties(Property[] properties) {
    if (properties.length == 0) {
      return null;
    }
    Properties props = new Properties();
    for (Property property : properties) {
      props.setProperty(property.name(), PropertyParser.parse(property.value(), configuration.getVariables()));
    }
    return props;
  }

  /**
   * 解析缓存引用
   */
  private void parseCacheRef() {
    CacheNamespaceRef cacheDomainRef = type.getAnnotation(CacheNamespaceRef.class);
    if (cacheDomainRef != null) {
      Class<?> refType = cacheDomainRef.value();
      String refName = cacheDomainRef.name();
      if (refType == void.class && refName.isEmpty()) {
        throw new BuilderException("Should be specified either value() or name() attribute in the @CacheNamespaceRef");
      }
      if (refType != void.class && !refName.isEmpty()) {
        throw new BuilderException("Cannot use both value() and name() attribute in the @CacheNamespaceRef");
      }
      String namespace = refType != void.class ? refType.getName() : refName;
      try {
        assistant.useCacheRef(namespace);
      } catch (IncompleteElementException e) {
        configuration.addIncompleteCacheRef(new CacheRefResolver(assistant, namespace));
      }
    }
  }

  /**
   * 解析resultMap
   *
   * @param method 方法
   * @return resultMapId
   */
  private String parseResultMap(Method method) {
    Class<?> returnType = getReturnType(method, type);
    Arg[] args = method.getAnnotationsByType(Arg.class);
    Result[] results = method.getAnnotationsByType(Result.class);
    TypeDiscriminator typeDiscriminator = method.getAnnotation(TypeDiscriminator.class);
    String resultMapId = generateResultMapName(method);
    applyResultMap(resultMapId, returnType, args, results, typeDiscriminator);
    return resultMapId;
  }

  /**
   * 生成resultMapId
   *
   * @param method 方法
   * @return resultMapId
   */
  private String generateResultMapName(Method method) {
    Results results = method.getAnnotation(Results.class);
    if (results != null && !results.id().isEmpty()) {
      return type.getName() + "." + results.id();
    }
    StringBuilder suffix = new StringBuilder();
    for (Class<?> c : method.getParameterTypes()) {
      suffix.append("-");
      suffix.append(c.getSimpleName());
    }
    if (suffix.isEmpty()) {
      suffix.append("-void");
    }
    return type.getName() + "." + method.getName() + suffix;
  }

  /**
   * 创建resultMap
   *
   * @param resultMapId   返回的MapId
   * @param returnType    返回的类型
   * @param args          参数
   * @param results       结果
   * @param discriminator 嵌套的Discriminator
   */
  private void applyResultMap(String resultMapId, Class<?> returnType, Arg[] args, Result[] results,
                              TypeDiscriminator discriminator) {
    List<ResultMapping> resultMappings = new ArrayList<>();
    applyConstructorArgs(args, returnType, resultMappings);
    applyResults(results, returnType, resultMappings);
    Discriminator disc = applyDiscriminator(resultMapId, returnType, discriminator);
    assistant.addResultMap(resultMapId, returnType, null, disc, resultMappings, null);
    createDiscriminatorResultMaps(resultMapId, returnType, discriminator);
  }

  /**
   * 创建嵌套的resultMap
   *
   * @param resultMapId   返回的MapId
   * @param resultType    返回的类型
   * @param discriminator 嵌套的Discriminator
   */
  private void createDiscriminatorResultMaps(String resultMapId, Class<?> resultType, TypeDiscriminator discriminator) {
    if (discriminator != null) {
      for (Case c : discriminator.cases()) {
        String caseResultMapId = resultMapId + "-" + c.value();
        List<ResultMapping> resultMappings = new ArrayList<>();
        // issue #136
        applyConstructorArgs(c.constructArgs(), resultType, resultMappings);
        applyResults(c.results(), resultType, resultMappings);
        assistant.addResultMap(caseResultMapId, c.type(), resultMapId, null, resultMappings, null);
      }
    }
  }

  /**
   * 创建Discriminator
   *
   * @param resultMapId   返回的MapId
   * @param resultType    返回的类型
   * @param discriminator 嵌套的Discriminator
   * @return Discriminator
   */
  private Discriminator applyDiscriminator(String resultMapId, Class<?> resultType, TypeDiscriminator discriminator) {
    if (discriminator != null) {
      String column = discriminator.column();
      Class<?> javaType = discriminator.javaType() == void.class ? String.class : discriminator.javaType();
      JdbcType jdbcType = discriminator.jdbcType() == JdbcType.UNDEFINED ? null : discriminator.jdbcType();
      @SuppressWarnings("unchecked")
      Class<? extends TypeHandler<?>> typeHandler = (Class<? extends TypeHandler<?>>) (discriminator
          .typeHandler() == UnknownTypeHandler.class ? null : discriminator.typeHandler());
      Case[] cases = discriminator.cases();
      Map<String, String> discriminatorMap = new HashMap<>();
      for (Case c : cases) {
        String value = c.value();
        String caseResultMapId = resultMapId + "-" + value;
        discriminatorMap.put(value, caseResultMapId);
      }
      return assistant.buildDiscriminator(resultType, column, javaType, jdbcType, typeHandler, discriminatorMap);
    }
    return null;
  }

  /**
   * 解析方法
   *
   * @param method 方法
   */
  public void parseStatement(Method method) {
    final Class<?> parameterTypeClass = getParameterType(method);
    final LanguageDriver languageDriver = getLanguageDriver(method);

    getAnnotationWrapper(method, true, statementAnnotationTypes).ifPresent(statementAnnotation -> {
      final SqlSource sqlSource = buildSqlSource(statementAnnotation.getAnnotation(), parameterTypeClass,
          languageDriver, method);
      final SqlCommandType sqlCommandType = statementAnnotation.getSqlCommandType();
      final Options options = getAnnotationWrapper(method, false, Options.class).map(x -> (Options) x.getAnnotation())
          .orElse(null);
      final String mappedStatementId = type.getName() + "." + method.getName();

      final KeyGenerator keyGenerator;
      String keyProperty = null;
      String keyColumn = null;
      if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
        // first check for SelectKey annotation - that overrides everything else
        SelectKey selectKey = getAnnotationWrapper(method, false, SelectKey.class)
            .map(x -> (SelectKey) x.getAnnotation()).orElse(null);
        if (selectKey != null) {
          keyGenerator = handleSelectKeyAnnotation(selectKey, mappedStatementId, getParameterType(method),
              languageDriver);
          keyProperty = selectKey.keyProperty();
        } else if (options == null) {
          keyGenerator = configuration.isUseGeneratedKeys() ? Jdbc3KeyGenerator.INSTANCE : NoKeyGenerator.INSTANCE;
        } else {
          keyGenerator = options.useGeneratedKeys() ? Jdbc3KeyGenerator.INSTANCE : NoKeyGenerator.INSTANCE;
          keyProperty = options.keyProperty();
          keyColumn = options.keyColumn();
        }
      } else {
        keyGenerator = NoKeyGenerator.INSTANCE;
      }

      Integer fetchSize = null;
      Integer timeout = null;
      StatementType statementType = StatementType.PREPARED;
      ResultSetType resultSetType = configuration.getDefaultResultSetType();
      boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
      boolean flushCache = !isSelect;
      boolean useCache = isSelect;
      if (options != null) {
        if (Options.FlushCachePolicy.TRUE.equals(options.flushCache())) {
          flushCache = true;
        } else if (Options.FlushCachePolicy.FALSE.equals(options.flushCache())) {
          flushCache = false;
        }
        useCache = options.useCache();
        // issue #348
        fetchSize = options.fetchSize() > -1 || options.fetchSize() == Integer.MIN_VALUE ? options.fetchSize() : null;
        timeout = options.timeout() > -1 ? options.timeout() : null;
        statementType = options.statementType();
        if (options.resultSetType() != ResultSetType.DEFAULT) {
          resultSetType = options.resultSetType();
        }
      }

      String resultMapId = null;
      if (isSelect) {
        ResultMap resultMapAnnotation = method.getAnnotation(ResultMap.class);
        if (resultMapAnnotation != null) {
          resultMapId = String.join(",", resultMapAnnotation.value());
        } else {
          resultMapId = generateResultMapName(method);
        }
      }

      assistant.addMappedStatement(mappedStatementId, sqlSource, statementType, sqlCommandType, fetchSize, timeout,
          // ParameterMapID
          null, parameterTypeClass, resultMapId, getReturnType(method, type), resultSetType, flushCache, useCache,
          // TODO gcode issue #577
          false, keyGenerator, keyProperty, keyColumn, statementAnnotation.getDatabaseId(), languageDriver,
          // ResultSets
          options != null ? nullOrEmpty(options.resultSets()) : null, statementAnnotation.isDirtySelect());
    });
  }

  /**
   * 获取语言驱动
   *
   * @param method 当前方法
   * @return LanguageDriver 语言驱动程序
   */
  private LanguageDriver getLanguageDriver(Method method) {
    Lang lang = method.getAnnotation(Lang.class);
    Class<? extends LanguageDriver> langClass = null;
    if (lang != null) {
      langClass = lang.value();
    }
    return configuration.getLanguageDriver(langClass);
  }

  /**
   * 获取参数类型
   *
   * @param method 当前方法
   * @return Class<?>
   */
  private Class<?> getParameterType(Method method) {
    Class<?> parameterType = null;
    Class<?>[] parameterTypes = method.getParameterTypes();
    for (Class<?> currentParameterType : parameterTypes) {
      if (!RowBounds.class.isAssignableFrom(currentParameterType)
          && !ResultHandler.class.isAssignableFrom(currentParameterType)) {
        if (parameterType == null) {
          parameterType = currentParameterType;
        } else {
          // issue #135
          parameterType = MapperMethod.ParamMap.class;
        }
      }
    }
    return parameterType;
  }

  /**
   * 获取返回类型
   *
   * @param method 当前方法
   * @param type   当前类
   * @return Class<?>
   */
  private static Class<?> getReturnType(Method method, Class<?> type) {
    Class<?> returnType = method.getReturnType();
    Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, type);
    if (resolvedReturnType instanceof Class) {
      returnType = (Class<?>) resolvedReturnType;
      if (returnType.isArray()) {
        returnType = returnType.getComponentType();
      }
      // gcode issue #508
      if (void.class.equals(returnType)) {
        ResultType rt = method.getAnnotation(ResultType.class);
        if (rt != null) {
          returnType = rt.value();
        }
      }
    } else if (resolvedReturnType instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) resolvedReturnType;
      Class<?> rawType = (Class<?>) parameterizedType.getRawType();
      if (Collection.class.isAssignableFrom(rawType) || Cursor.class.isAssignableFrom(rawType)) {
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments != null && actualTypeArguments.length == 1) {
          Type returnTypeParameter = actualTypeArguments[0];
          if (returnTypeParameter instanceof Class<?>) {
            returnType = (Class<?>) returnTypeParameter;
          } else if (returnTypeParameter instanceof ParameterizedType) {
            // (gcode issue #443) actual type can be a also a parameterized type
            returnType = (Class<?>) ((ParameterizedType) returnTypeParameter).getRawType();
          } else if (returnTypeParameter instanceof GenericArrayType) {
            Class<?> componentType = (Class<?>) ((GenericArrayType) returnTypeParameter).getGenericComponentType();
            // (gcode issue #525) support List<byte[]>
            returnType = Array.newInstance(componentType, 0).getClass();
          }
        }
      } else if (method.isAnnotationPresent(MapKey.class) && Map.class.isAssignableFrom(rawType)) {
        // (gcode issue 504) Do not look into Maps if there is not MapKey annotation
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments != null && actualTypeArguments.length == 2) {
          Type returnTypeParameter = actualTypeArguments[1];
          if (returnTypeParameter instanceof Class<?>) {
            returnType = (Class<?>) returnTypeParameter;
          } else if (returnTypeParameter instanceof ParameterizedType) {
            // (gcode issue 443) actual type can be a also a parameterized type
            returnType = (Class<?>) ((ParameterizedType) returnTypeParameter).getRawType();
          }
        }
      } else if (Optional.class.equals(rawType)) {
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Type returnTypeParameter = actualTypeArguments[0];
        if (returnTypeParameter instanceof Class<?>) {
          returnType = (Class<?>) returnTypeParameter;
        }
      } else if (IPage.class.isAssignableFrom(rawType)) {
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Type returnTypeParameter = actualTypeArguments[0];
        if (returnTypeParameter instanceof Class<?>) {
          returnType = (Class<?>) returnTypeParameter;
        } else if (returnTypeParameter instanceof ParameterizedType) {
          returnType = (Class<?>) ((ParameterizedType) returnTypeParameter).getRawType();
        }
      }
    }

    return returnType;
  }

  /**
   * 应用结果
   *
   * @param results        列
   * @param resultType     实体类型
   * @param resultMappings 映射
   */
  private void applyResults(Result[] results, Class<?> resultType, List<ResultMapping> resultMappings) {
    for (Result result : results) {
      List<ResultFlag> flags = new ArrayList<>();
      if (result.id()) {
        flags.add(ResultFlag.ID);
      }
      @SuppressWarnings("unchecked")
      Class<? extends TypeHandler<?>> typeHandler = (Class<? extends TypeHandler<?>>) (result
          .typeHandler() == UnknownTypeHandler.class ? null : result.typeHandler());
      boolean hasNestedResultMap = hasNestedResultMap(result);
      ResultMapping resultMapping = assistant.buildResultMapping(resultType, nullOrEmpty(result.property()),
          nullOrEmpty(result.column()), result.javaType() == void.class ? null : result.javaType(),
          result.jdbcType() == JdbcType.UNDEFINED ? null : result.jdbcType(),
          hasNestedSelect(result) ? nestedSelectId(result) : null,
          hasNestedResultMap ? nestedResultMapId(result) : null, null,
          hasNestedResultMap ? findColumnPrefix(result) : null, typeHandler, flags, null, null, isLazy(result));
      resultMappings.add(resultMapping);
    }
  }

  /**
   * 列前缀
   *
   * @param result 列
   * @return String
   */
  private String findColumnPrefix(Result result) {
    String columnPrefix = result.one().columnPrefix();
    if (columnPrefix.isEmpty()) {
      columnPrefix = result.many().columnPrefix();
    }
    return columnPrefix;
  }

  /**
   * 嵌套结果映射ID
   *
   * @param result 列
   * @return String
   */
  private String nestedResultMapId(Result result) {
    String resultMapId = result.one().resultMap();
    if (resultMapId.isEmpty()) {
      resultMapId = result.many().resultMap();
    }
    if (!resultMapId.contains(StringUtils.DOT)) {
      resultMapId = type.getName() + StringUtils.DOT + resultMapId;
    }
    return resultMapId;
  }

  /**
   * 是否嵌套结果
   *
   * @param result 列
   * @return boolean
   */
  private boolean hasNestedResultMap(Result result) {
    if (!result.one().resultMap().isEmpty() && !result.many().resultMap().isEmpty()) {
      throw new BuilderException("Cannot use both @One and @Many annotations in the same @Result");
    }
    return !result.one().resultMap().isEmpty() || !result.many().resultMap().isEmpty();
  }

  /**
   * 嵌套选择
   *
   * @param result 列
   * @return String
   */
  private String nestedSelectId(Result result) {
    String nestedSelect = result.one().select();
    if (nestedSelect.isEmpty()) {
      nestedSelect = result.many().select();
    }
    if (!nestedSelect.contains(StringUtils.DOT)) {
      nestedSelect = type.getName() + StringUtils.DOT + nestedSelect;
    }
    return nestedSelect;
  }

  /**
   * 是否懒加载
   *
   * @param result 列
   * @return boolean
   */
  private boolean isLazy(Result result) {
    boolean isLazy = configuration.isLazyLoadingEnabled();
    if (!result.one().select().isEmpty() && FetchType.DEFAULT != result.one().fetchType()) {
      isLazy = result.one().fetchType() == FetchType.LAZY;
    } else if (!result.many().select().isEmpty() && FetchType.DEFAULT != result.many().fetchType()) {
      isLazy = result.many().fetchType() == FetchType.LAZY;
    }
    return isLazy;
  }

  /**
   * 已嵌套选择
   *
   * @param result 列
   * @return boolean
   */
  private boolean hasNestedSelect(Result result) {
    if (!result.one().select().isEmpty() && !result.many().select().isEmpty()) {
      throw new BuilderException("Cannot use both @One and @Many annotations in the same @Result");
    }
    return !result.one().select().isEmpty() || !result.many().select().isEmpty();
  }

  /**
   * 应用构造函数参数
   *
   * @param args           构造函数参数
   * @param resultType     结果类型
   * @param resultMappings 结果映射
   */
  private void applyConstructorArgs(Arg[] args, Class<?> resultType, List<ResultMapping> resultMappings) {
    for (Arg arg : args) {
      List<ResultFlag> flags = new ArrayList<>();
      flags.add(ResultFlag.CONSTRUCTOR);
      if (arg.id()) {
        flags.add(ResultFlag.ID);
      }
      @SuppressWarnings("unchecked")
      Class<? extends TypeHandler<?>> typeHandler = (Class<? extends TypeHandler<?>>) (arg
          .typeHandler() == UnknownTypeHandler.class ? null : arg.typeHandler());
      ResultMapping resultMapping = assistant.buildResultMapping(resultType, nullOrEmpty(arg.name()),
          nullOrEmpty(arg.column()), arg.javaType() == void.class ? null : arg.javaType(),
          arg.jdbcType() == JdbcType.UNDEFINED ? null : arg.jdbcType(), nullOrEmpty(arg.select()),
          nullOrEmpty(arg.resultMap()), null, nullOrEmpty(arg.columnPrefix()), typeHandler, flags, null, null, false);
      resultMappings.add(resultMapping);
    }
  }

  /**
   * 如果为空，则返回空
   *
   * @param value 值
   * @return 如果为空，则返回空
   */
  private String nullOrEmpty(String value) {
    return value == null || value.trim().isEmpty() ? null : value;
  }

  /**
   * 控制柄选择键注释
   *
   * @param selectKeyAnnotation 选择关键注释
   * @param baseStatementId     基础语句ID
   * @param parameterTypeClass  参数类型类
   * @param languageDriver      语言驱动
   * @return 键生成器
   */
  private KeyGenerator handleSelectKeyAnnotation(SelectKey selectKeyAnnotation, String baseStatementId,
                                                 Class<?> parameterTypeClass, LanguageDriver languageDriver) {
    String id = baseStatementId + SelectKeyGenerator.SELECT_KEY_SUFFIX;
    Class<?> resultTypeClass = selectKeyAnnotation.resultType();
    StatementType statementType = selectKeyAnnotation.statementType();
    String keyProperty = selectKeyAnnotation.keyProperty();
    String keyColumn = selectKeyAnnotation.keyColumn();
    boolean executeBefore = selectKeyAnnotation.before();

    // defaults
    boolean useCache = false;
    KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
    Integer fetchSize = null;
    Integer timeout = null;
    boolean flushCache = false;
    String parameterMap = null;
    String resultMap = null;
    ResultSetType resultSetTypeEnum = null;
    String databaseId = selectKeyAnnotation.databaseId().isEmpty() ? null : selectKeyAnnotation.databaseId();

    SqlSource sqlSource = buildSqlSource(selectKeyAnnotation, parameterTypeClass, languageDriver, null);
    SqlCommandType sqlCommandType = SqlCommandType.SELECT;

    assistant.addMappedStatement(id, sqlSource, statementType, sqlCommandType, fetchSize, timeout, parameterMap,
        parameterTypeClass, resultMap, resultTypeClass, resultSetTypeEnum, flushCache, useCache, false, keyGenerator,
        keyProperty, keyColumn, databaseId, languageDriver, null, false);

    id = assistant.applyCurrentNamespace(id, false);

    MappedStatement keyStatement = configuration.getMappedStatement(id, false);
    SelectKeyGenerator answer = new SelectKeyGenerator(keyStatement, executeBefore);
    configuration.addKeyGenerator(id, answer);
    return answer;
  }

  /**
   * 构建sqlSource
   *
   * @param annotation     注解
   * @param parameterType  参数类型
   * @param languageDriver 语言驱动
   * @param method         方法
   * @return SqlSource
   */
  private SqlSource buildSqlSource(Annotation annotation, Class<?> parameterType, LanguageDriver languageDriver,
                                   Method method) {
    if (annotation instanceof Select) {
      return buildSqlSourceFromStrings(((Select) annotation).value(), parameterType, languageDriver);
    }
    if (annotation instanceof Update) {
      return buildSqlSourceFromStrings(((Update) annotation).value(), parameterType, languageDriver);
    } else if (annotation instanceof Insert) {
      return buildSqlSourceFromStrings(((Insert) annotation).value(), parameterType, languageDriver);
    } else if (annotation instanceof Delete) {
      return buildSqlSourceFromStrings(((Delete) annotation).value(), parameterType, languageDriver);
    } else if (annotation instanceof SelectKey) {
      return buildSqlSourceFromStrings(((SelectKey) annotation).statement(), parameterType, languageDriver);
    }
    return new ProviderSqlSource(assistant.getConfiguration(), annotation, type, method);
  }

  /**
   * 构建sqlSource
   *
   * @param strings            字符串
   * @param parameterTypeClass 参数类型
   * @param languageDriver     语言驱动
   * @return SqlSource
   */
  private SqlSource buildSqlSourceFromStrings(String[] strings, Class<?> parameterTypeClass,
                                              LanguageDriver languageDriver) {
    return languageDriver.createSqlSource(configuration, String.join(" ", strings).trim(), parameterTypeClass);
  }

  /**
   * 获取注解包装类
   *
   * @param method         方法
   * @param errorIfNoMatch 是否报错
   * @param targetTypes    目标类型
   * @return Optional<AnnotationWrapper>
   */
  @SafeVarargs
  private final Optional<AnnotationWrapper> getAnnotationWrapper(Method method, boolean errorIfNoMatch,
                                                                 Class<? extends Annotation>... targetTypes) {
    return getAnnotationWrapper(method, errorIfNoMatch, Arrays.asList(targetTypes));
  }

  /**
   * 获取注解包装类
   *
   * @param method         方法
   * @param errorIfNoMatch 是否报错
   * @param targetTypes    目标类型
   * @return Optional<AnnotationWrapper>
   */
  private Optional<AnnotationWrapper> getAnnotationWrapper(Method method, boolean errorIfNoMatch,
                                                           Collection<Class<? extends Annotation>> targetTypes) {
    String databaseId = configuration.getDatabaseId();
    Map<String, AnnotationWrapper> statementAnnotations = targetTypes.stream()
        .flatMap(x -> Arrays.stream(method.getAnnotationsByType(x))).map(AnnotationWrapper::new)
        .collect(Collectors.toMap(AnnotationWrapper::getDatabaseId, x -> x, (existing, duplicate) -> {
          throw new BuilderException(
              String.format("Detected conflicting annotations '%s' and '%s' on '%s'.", existing.getAnnotation(),
                  duplicate.getAnnotation(), method.getDeclaringClass().getName() + "." + method.getName()));
        }));
    AnnotationWrapper annotationWrapper = null;
    if (databaseId != null) {
      annotationWrapper = statementAnnotations.get(databaseId);
    }
    if (annotationWrapper == null) {
      annotationWrapper = statementAnnotations.get("");
    }
    if (errorIfNoMatch && annotationWrapper == null && !statementAnnotations.isEmpty()) {
      // Annotations exist, but there is no matching one for the specified databaseId
      throw new BuilderException(String.format(
          "Could not find a statement annotation that correspond a current database or default statement on method '%s.%s'. Current database id is [%s].",
          method.getDeclaringClass().getName(), method.getName(), databaseId));
    }
    return Optional.ofNullable(annotationWrapper);
  }

  /**
   * 获取方法返回类型
   *
   * @param mapperFqn        mapperFqn
   * @param localStatementId 本地报表Id
   * @return Class<?>
   */
  public static Class<?> getMethodReturnType(String mapperFqn, String localStatementId) {
    if (mapperFqn == null || localStatementId == null) {
      return null;
    }
    try {
      Class<?> mapperClass = Resources.classForName(mapperFqn);
      for (Method method : mapperClass.getMethods()) {
        if (method.getName().equals(localStatementId) && canHaveStatement(method)) {
          return getReturnType(method, mapperClass);
        }
      }
    } catch (ClassNotFoundException e) {
      // No corresponding mapper interface which is OK
    }
    return null;
  }

  /**
   * 注释包装器
   */
  private static class AnnotationWrapper {

    /**
     * 注解
     */
    private final Annotation annotation;
    /**
     * 数据库Id
     */
    private final String databaseId;

    /**
     * Sql命令类型
     */
    private final SqlCommandType sqlCommandType;

    /**
     * 是否脏查询
     */
    private boolean dirtySelect;

    /**
     * 构造函数
     *
     * @param annotation 注解
     */
    AnnotationWrapper(Annotation annotation) {
      this.annotation = annotation;
      if (annotation instanceof Select) {
        databaseId = ((Select) annotation).databaseId();
        sqlCommandType = SqlCommandType.SELECT;
        dirtySelect = ((Select) annotation).affectData();
      } else if (annotation instanceof Update) {
        databaseId = ((Update) annotation).databaseId();
        sqlCommandType = SqlCommandType.UPDATE;
      } else if (annotation instanceof Insert) {
        databaseId = ((Insert) annotation).databaseId();
        sqlCommandType = SqlCommandType.INSERT;
      } else if (annotation instanceof Delete) {
        databaseId = ((Delete) annotation).databaseId();
        sqlCommandType = SqlCommandType.DELETE;
      } else if (annotation instanceof SelectProvider) {
        databaseId = ((SelectProvider) annotation).databaseId();
        sqlCommandType = SqlCommandType.SELECT;
        dirtySelect = ((SelectProvider) annotation).affectData();
      } else if (annotation instanceof UpdateProvider) {
        databaseId = ((UpdateProvider) annotation).databaseId();
        sqlCommandType = SqlCommandType.UPDATE;
      } else if (annotation instanceof InsertProvider) {
        databaseId = ((InsertProvider) annotation).databaseId();
        sqlCommandType = SqlCommandType.INSERT;
      } else if (annotation instanceof DeleteProvider) {
        databaseId = ((DeleteProvider) annotation).databaseId();
        sqlCommandType = SqlCommandType.DELETE;
      } else {
        sqlCommandType = SqlCommandType.UNKNOWN;
        if (annotation instanceof Options) {
          databaseId = ((Options) annotation).databaseId();
        } else if (annotation instanceof SelectKey) {
          databaseId = ((SelectKey) annotation).databaseId();
        } else {
          databaseId = StringUtils.EMPTY;
        }
      }
    }

    /**
     * 获取注解
     *
     * @return Annotation
     */
    Annotation getAnnotation() {
      return annotation;
    }

    /**
     * 获取Sql命令类型
     *
     * @return SqlCommandType
     */
    SqlCommandType getSqlCommandType() {
      return sqlCommandType;
    }

    /**
     * 获取数据库Id
     *
     * @return String
     */
    String getDatabaseId() {
      return databaseId;
    }

    /**
     * 是否脏查询
     *
     * @return boolean
     */
    boolean isDirtySelect() {
      return dirtySelect;
    }
  }
}
