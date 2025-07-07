package com.cdkjframework.util.files;

import com.cdkjframework.constant.EncodingConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.RequestUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.util.tool
 * @ClassName: XmlUtil
 * @Description: XML 操作工具
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class XmlUtils {

  /**
   * 日志
   */
  private static LogUtils logUtil = LogUtils.getLogger(XmlUtils.class);

  /**
   * mode
   */
  private static boolean mode = true;

  /**
   * 将 xml 转换为实体
   *
   * @param clazz 实体
   * @param xml   xml 数据
   * @param <T>   类型
   * @return T 返回实体数据
   */
  public static <T> T xmlToBean(Class<T> clazz, InputStream xml) {
    T t = null;
    try {
      String xmlStr = RequestUtils.inputStreamToString(xml);
      t = xmlToBean(clazz, xmlStr);
    } catch (Exception ex) {
      logUtil.error("[XStream] xml 转对象出错:" + ex.getMessage());
    }

    //返回结果
    return t;
  }

  /**
   * 将 xml 转换为实体
   *
   * @param clazz 实体
   * @param xml   xml 数据
   * @param <T>   类型
   * @return T 返回实体数据
   */
  public static <T> T xmlToBean(Class<T> clazz, String xml) {
    T t = null;
    try {
      XStream xstream = new XStream(new DomDriver(EncodingConsts.UTF8));
      xstream.addPermission(AnyTypePermission.ANY);
      xstream.processAnnotations(clazz);
      xstream.autodetectAnnotations(mode);
      xstream.setClassLoader(clazz.getClassLoader());
      t = (T) xstream.fromXML(xml);
    } catch (Exception ex) {
      logUtil.error("[XStream] xml 转对象出错:" + ex.getMessage());
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
    String result = StringUtils.Empty;
    try {
      XStream xstream = new XStream(new DomDriver(EncodingConsts.UTF8));
      xstream.addPermission(AnyTypePermission.ANY);
      xstream.processAnnotations(clazz);
      xstream.autodetectAnnotations(mode);
      String regex = StringUtils.HORIZONTAL + StringUtils.HORIZONTAL, replacement = StringUtils.HORIZONTAL;
      result = xstream.toXML(obj).replaceAll(regex, replacement);
    } catch (Exception ex) {
      logUtil.error("[XStream] bean 转对象出错:{}" + ex.getCause());
    }

    //返回结果
    return result;
  }
}
