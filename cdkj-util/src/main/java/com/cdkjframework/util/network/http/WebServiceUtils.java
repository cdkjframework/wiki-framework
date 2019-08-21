package com.cdkjframework.util.network.http;

import com.cdkjframework.entity.http.WebServiceEntity;
import com.cdkjframework.util.files.XmlUtil;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.util.http
 * @ClassName: WebServiceUtil
 * @Description: web service 请求工具
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class WebServiceUtils<T> {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(WebServiceUtils.class);


    /**
     * post 方式请求WebService
     *
     * @param entity 请求数据实体
     * @param clazz  要转换的实体
     * @return 返回结果
     */
    public static <T> T postWebService(WebServiceEntity entity, Class<T> clazz) {
        String result = postWebService(entity);
        String name = "java.util.ArrayList";
        T t = null;
        switch (entity.getDataFormat()) {
            case "xml":
                t = XmlUtil.xmlToBean(clazz, result);
                break;
            default:
                if (name.equals(clazz.getName())) {
                    t = (T) JsonUtils.jsonStringToList(result, clazz);
                } else {
                    t = JsonUtils.jsonStringToBean(result, clazz);
                }
                break;
        }
        //返回结果
        return t;
    }

    /**
     * post 方式请求WebService
     *
     * @param entity 请求数据实体
     * @return 返回结果
     */
    public static String postWebService(WebServiceEntity entity) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf(postWebServiceObject(entity)));
        //返回结果
        return builder.toString();
    }

    /**
     * post 方式请求WebService
     *
     * @param entity 请求数据实体
     * @return 返回结果
     */
    public static Object[] postWebServiceObject(WebServiceEntity entity) {
        //创建实例
        JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();

        //创建连接
        Client client = clientFactory.createClient(entity.getWebServiceAddress());

        // 需要密码的情况需要加上用户名和密码
        if (!StringUtils.isNullAndSpaceOrEmpty(entity.getUserName()) && !StringUtils.isNullAndSpaceOrEmpty(entity.getPassword())) {
            client.getOutInterceptors().add(new LoggingInInterceptor(entity.getUserName(), entity.getPassword()));
        }

        Object[] result = null;
        try {
            //如果有命名空间的话
            //如果有命名空间需要加上这个，第一个参数为命名空间名称，第二个参数为WebService方法名称
            if (!StringUtils.isNullAndSpaceOrEmpty(entity.getNamespace())) {
                QName operationName = new QName(entity.getNamespace(), entity.getMethodName());
                result = client.invoke(operationName, entity.getParams());
            } else {
                result = client.invoke(entity.getMethodName(), entity.getParams());
            }
        } catch (Exception ex) {
            result = new Object[]{};
            logUtil.error(ex.getMessage());
        }
        //返回结果
        return result;
    }
}
