package com.cdkjframework.message.queue.aliyun.rocket.client;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.cdkjframework.config.AliCloudRocketMqClientConfig;
import com.cdkjframework.config.AliCloudRocketMqConfig;
import com.cdkjframework.constant.Application;
import com.cdkjframework.entity.message.aliyun.RocketMqCallbackEntity;
import com.cdkjframework.util.log.LogUtil;
import com.cdkjframework.util.tool.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkj.framework.core.message.queue.aliyun.rocket.client
 * @ClassName: AliCloudRocketMqClientBean
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
@Order(2)
public class RocketMqMessageListener implements MessageListener, ApplicationRunner {

    /**
     * 日志
     */
    private static LogUtil logUtil = LogUtil.getLogger(RocketMqMessageListener.class);

    /**
     * 消息配置
     */
    @Autowired
    private AliCloudRocketMqClientConfig aliCloudRocketMqConfig;

    /**
     * 方法
     */
    private Method method;

    /**
     * bean
     */
    private Object bean;

    /**
     * 定义顺序加载
     *
     * @param args 参数
     * @throws Exception 异常
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        getBean();
    }

    /**
     * 订阅消息回传
     *
     * @param message        消息内容
     * @param consumeContext
     * @return
     */
    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        if (Application.applicationContext == null) {
            // 返回重发
            return Action.ReconsumeLater;
        }

        if (method == null) {
            getBean();
            // 此处为了不影响使用
            consume(message, consumeContext);
            logUtil.error("未设置回调方法，消息内容为：" + JsonUtil.objectToJsonString(message));
        }

        //调用参数
        try {
            RocketMqCallbackEntity callbackEntity = new RocketMqCallbackEntity();
            callbackEntity.setBornTimestamp(message.getBornTimestamp());
            callbackEntity.setMessage(new String(message.getBody()));
            callbackEntity.setMessageId(message.getMsgID());
            callbackEntity.setTag(message.getTag());
            callbackEntity.setTopic(message.getTopic());
            callbackEntity.setMessageType(0);

            method.invoke(bean, callbackEntity);

            //如果想测试消息重投的功能,可以将Action.CommitMessage 替换成Action.ReconsumeLater
            return Action.CommitMessage;
        } catch (IllegalAccessException e) {
            logUtil.error(e.getMessage());
        } catch (InvocationTargetException e) {
            logUtil.error(e.getMessage());
        }

        //返确认收到
        return Action.ReconsumeLater;
    }

    /**
     * 获取 bean
     */
    public void getBean() {
        try {
            // 使用spring content 获取类的实例 必须在 application 注册 applicationContext 变量
            ApplicationContext context = Application.applicationContext;
            if (context == null) {
                return;
            }
            Class targetClass = Class.forName(aliCloudRocketMqConfig.getClassName());
            this.bean = context.getBean(targetClass);
            /*
             给实例化的类注入需要的bean (@Autowired)
             如果不注入，被@Autowired注解的变量会报空指针
              */
            context.getAutowireCapableBeanFactory().autowireBean(bean);

            this.method = targetClass.getDeclaredMethod(aliCloudRocketMqConfig.getMethodName(), RocketMqCallbackEntity.class);
            // 设置访问权限
            this.method.setAccessible(true);
        } catch (ClassNotFoundException e) {
            logUtil.error(e.getMessage());
        } catch (NoSuchMethodException e) {
            logUtil.error(e.getMessage());
        }
    }
}
