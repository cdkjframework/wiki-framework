package com.cdkjframework.message.queue.aliyun.rocket.server;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import com.cdkjframework.config.AliCloudRocketMqConfig;
import com.cdkjframework.constant.Application;
import com.cdkjframework.entity.message.aliyun.RocketMqCallbackEntity;
import com.cdkjframework.message.queue.aliyun.rocket.client.RocketMqMessageListener;
import com.cdkjframework.util.log.LogUtil;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.message.queue.aliyun.rocket.server
 * @ClassName: LocalTransactionCheckerListener
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class LocalTransactionCheckerListener implements LocalTransactionChecker {

    /**
     * 日志
     */
    private static LogUtil logUtil = LogUtil.getLogger(RocketMqMessageListener.class);

    /**
     * 消息配置
     */
    private AliCloudRocketMqConfig aliCloudRocketMqConfig;

    /**
     * 方法
     */
    private Method method;

    /**
     * bean
     */
    private Object bean;

    /**
     * 构造函数
     */
    public LocalTransactionCheckerListener() {
        try {
            // 使用spring content 获取类的实例 必须在 application 注册 applicationContext 变量
            ApplicationContext context = Application.applicationContext;
            if (context == null) {
                return;
            }
            aliCloudRocketMqConfig = context.getBean(AliCloudRocketMqConfig.class);
            Class targetClass = Class.forName(aliCloudRocketMqConfig.getClassName());
            bean = context.getBean(targetClass);
            /*
             给实例化的类注入需要的bean (@Autowired)
             如果不注入，被@Autowired注解的变量会报空指针
              */
            context.getAutowireCapableBeanFactory().autowireBean(bean);

            Method method = targetClass.getDeclaredMethod(aliCloudRocketMqConfig.getMethodName(), Message.class);
            // 设置访问权限
            method.setAccessible(true);
        } catch (ClassNotFoundException e) {
            logUtil.error(e.getMessage());
        } catch (NoSuchMethodException e) {
            logUtil.error(e.getMessage());
        }
    }

    /**
     * 检查
     *
     * @param message 消息
     * @return 返回结果
     */
    @Override
    public TransactionStatus check(Message message) {
        try {
            RocketMqCallbackEntity callbackEntity = new RocketMqCallbackEntity();
            callbackEntity.setBornTimestamp(message.getBornTimestamp());
            callbackEntity.setMessage(String.valueOf(message.getBody()));
            callbackEntity.setMessageId(message.getMsgID());
            callbackEntity.setTag(message.getTag());
            callbackEntity.setTopic(message.getTopic());
            callbackEntity.setMessageType(0);

            method.invoke(bean, message);

            //如果想测试消息重投的功能,可以将Action.CommitMessage 替换成Action.ReconsumeLater
            return TransactionStatus.CommitTransaction;
        } catch (IllegalAccessException e) {
            logUtil.error(e.getMessage());
        } catch (InvocationTargetException e) {
            logUtil.error(e.getMessage());
        }

        //返回空
        return TransactionStatus.RollbackTransaction;
    }
}
