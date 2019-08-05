package com.cdkjframework.util.files.freemarker;

import com.cdkjframework.entity.generate.template.GenerateEntity;
import com.cdkjframework.util.log.LogUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.util.files.freemarker
 * @ClassName: FreemarkerUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class FreemarkerUtil {

    /**
     * 日志
     */
    private static LogUtil logUtil = LogUtil.getLogger(FreemarkerUtil.class);

    /**
     * 解析模板
     *
     * @param template 模板名称
     * @param entity   数据
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String analyticalTemplate(String template, GenerateEntity entity) throws IOException, TemplateException {
        //设置配置信息
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setDefaultEncoding("UTF-8");
        //读取模板
        ClassPathResource classPathResource = new ClassPathResource("/com/cdkj/framework/templates/" + template + ".ftl");

        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;

        //字符串缓冲区
        StringBuffer stringBuffer = new StringBuffer();
        try {
            //将文件读取为流
            reader = new InputStreamReader(classPathResource.getInputStream());
            bufferedReader = new BufferedReader(reader);

            //读取模板信息
            String lineString;
            while ((lineString = bufferedReader.readLine()) != null) {
                stringBuffer.append(lineString).append("\r\n");
            }
        } catch (Exception e) {
            logUtil.error(e);
        } finally {
            bufferedReader.close();
            reader.close();
        }
        //设置模板
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate(template, stringBuffer.toString());
        cfg.setTemplateLoader(templateLoader);

        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        //生成模板
        Template temp = cfg.getTemplate(template);
        StringWriter stringWriter = new StringWriter();
        temp.process(entity, stringWriter);

        //返回解析结果
        return stringWriter.toString();
    }
}
