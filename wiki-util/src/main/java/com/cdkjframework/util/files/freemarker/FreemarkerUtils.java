package com.cdkjframework.util.files.freemarker;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.EncodingConsts;
import com.cdkjframework.entity.generate.template.GenerateEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: FreemarkerUtils
 * @Description: Freemarker模板工具
 * @Author: xiaLin
 * @Date: 2023/6/14 20:51
 * @Version: 1.0
 */
@Component
public class FreemarkerUtils {

  /**
   * 日志
   */
  private LogUtils logUtils = LogUtils.getLogger(FreemarkerUtils.class);

  /**
   * 模版配置对象
   */
  private final Configuration cfg;

  /**
   * 配置
   */
  private final CustomConfig customConfig;

  /**
   * 获取实例
   *
   * @return 返回实例
   */
  public static FreemarkerUtils getInstance(CustomConfig customConfig) {
    return new FreemarkerUtils(customConfig);
  }

  /**
   * 构建函数
   *
   * @param customConfig
   */
  public FreemarkerUtils(CustomConfig customConfig) {
    this.customConfig = customConfig;
    // 创建一个Configuration实例，建议带参，
    // 不带参的构造方法被标明为过期了，这里我用的是  freemarker-2.3.23.jar
    // 可以从  maven repository 官网下载  jar 包 ，不会下载请拉最后
    cfg = new Configuration(Configuration.VERSION_2_3_28);
    // 设置默认编码
    cfg.setDefaultEncoding(EncodingConsts.UTF8);

    // 所有模板都在一个文件夹路径
    try {
      if (StringUtils.isNotNullAndEmpty(customConfig.getTemplatePath())) {
        cfg.setDirectoryForTemplateLoading(new File(customConfig.getTemplatePath()));
      }

      // ftl模板文件统一放至 com.testExample.freemarker.template 包下面
      if (StringUtils.isNotNullAndEmpty(customConfig.getClassTemplate())) {
        cfg.setClassForTemplateLoading(FreemarkerUtils.class, customConfig.getClassTemplate());
      }
    } catch (IOException e) {
      logUtils.error(e);
    }
    //错误控制器，控制异常，详情看图4
    //RETHROW_HANDLER ：错误信息会输出到控制台
    //HTML_DEBUG_HANDLER : 错误信息会输出到你要生成的html页面，详情看图4_1
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    cfg.setLogTemplateExceptions(false);
    cfg.setWrapUncheckedExceptions(true);
  }

  /**
   * 解析模板
   *
   * @param template 模板名称
   * @param entity   数据
   * @return
   * @throws IOException
   * @throws TemplateException
   */
  public String analyticalTemplate(String template, GenerateEntity entity) throws IOException, TemplateException {
    // 读取模板
    /**
     * 后缀
     */
    String suffix = ".ftl";
    if (!template.endsWith(suffix)) {
      template = template + suffix;
    }
    return analyticalTemplateString(template, entity);
  }

  /**
   * 解析模板
   *
   * @param template 模板名称
   * @param entity   数据
   * @param template 路径
   * @return
   * @throws IOException
   * @throws TemplateException
   */
  public String analyticalTemplateString(String template, GenerateEntity entity) throws IOException, TemplateException {
    // 生成模板
    Template temp = cfg.getTemplate(template);
    StringWriter stringWriter = new StringWriter();
    temp.process(entity, stringWriter);

    //返回解析结果
    return stringWriter.toString();
  }

  /**
   * 过程
   *
   * @param templateName   模板名称
   * @param targetFileName 目标文件
   * @param root           数据
   * @throws Exception 异常信息
   */
  private void process(String templateName, String targetFileName, Map<String, Object> root) throws Exception {
    Writer out = null;
    try {
      File file = new File(targetFileName);
      if (!file.exists()) {
        file.createNewFile();
        // 因为是生成到 Linux 上，需要设置权限，自己写的
        // 设置可执行权限，第二个参数默认为true（表示root权限才可以执行该文件，false为所有人都可以）
        file.setExecutable(true, false);
        // 设置可读权限，第二个参数默认为true（同上）
        file.setReadable(true, false);
        // 设置可写权限，第二个参数默认为true（这里可以省略不写）
        file.setWritable(true);
      }

      // 创建模版对象
      Template t = cfg.getTemplate(templateName);

      // 设置输出流，详情看 设置编码  UTF-8
      out = new OutputStreamWriter(new FileOutputStream(file), EncodingConsts.UTF8);
      // 在模版上执行插值操作，并输出到制定的输出流中
      // root 是模板ftl中的变量的值
      t.process(root, out);
    } catch (Exception e) {
      logUtils.error(e);
    } finally {
      if (null != out) {
        try {
          out.flush();
          out.close();
        } catch (IOException e) {
          logUtils.error(e);
        }
      }
    }
  }
}
