package com.cdkjframework.util.files;

import com.cdkjframework.util.log.LogUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.core.util.tool
 * @ClassName: XmlUtil
 * @Description: XML 操作工具
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class XmlUtil {

    /**
     * 日志
     */
    private static LogUtil logUtil = LogUtil.getLogger(XmlUtil.class);

    /**
     * 将 xml 转换为实体
     *
     * @param clazz 实体
     * @param xml   xml 数据
     * @param <T>   返回实体数据
     * @return T
     */
    public static <T> T xmlToBean(Class<T> clazz, String xml) {
        T t = null;
        try {
            XStream xstream = new XStream(new DomDriver("utf-8"));
            xstream.processAnnotations(clazz);
            xstream.autodetectAnnotations(true);
            xstream.setClassLoader(clazz.getClassLoader());
            t = (T) xstream.fromXML(xml);
        } catch (Exception ex) {
            logUtil.error("[XStream]XML转对象出错:" + ex.getMessage());
        }

        //返回结果
        return t;
    }

    /**
     * 将 bean 转换为实体
     *
     * @param obj 实体数据
     * @return 返回 String
     */
    public static String beanToXml(Class clazz, Object obj) {
        String reulst = "";
        try {
            XStream xstream = new XStream(new DomDriver("utf-8"));
            xstream.processAnnotations(clazz);
            xstream.autodetectAnnotations(true);

            reulst = xstream.toXML(obj).replaceAll("__", "_");
        } catch (Exception ex) {
            logUtil.error("[XStream]bean转对象出错:{}" + ex.getCause());
        }

        //返回结果
        return reulst;
    }
}
