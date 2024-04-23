package com.cdkjframework.util.tool;

import com.cdkjframework.config.MailConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.log.LogUtils;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: SendMail
 * @Description: 发送邮件
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class SendMailUtils {
    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(SendMailUtils.class);

    /**
     * 邮件配置
     */
    private static MailConfig mailConfig;

    /**
     * 创建邮件发送信息
     */
    private static JavaMailSenderImpl mailSender;

    /**
     * 构造函数
     */
    @Autowired
    public SendMailUtils(MailConfig config) {
      if (StringUtils.isNullAndSpaceOrEmpty(config.getHost())) {
        return;
      }
      mailConfig = config;
      run();
    }

    /**
     * 发送邮件
     *
     * @param toMail  收件邮箱
     * @param subject 主主题
     * @param message 信息
     */
    public static void sendMail(List<String> toMail, String subject, String message) throws IOException, MessagingException, GlobalException {
        sendMail(toMail, subject, message, new ArrayList<>(), StringUtils.NullObject, null);
    }

    /**
     * 发送邮件
     *
     * @param toMail  收件邮箱
     * @param subject 主主题
     * @param message 信息
     * @param ccMail  抄送邮箱
     */
    public static void sendMail(List<String> toMail, String subject, String message, List<String> ccMail) throws IOException, MessagingException, GlobalException {
        sendMail(toMail, subject, message, ccMail, StringUtils.NullObject, null);
    }

    /**
     * 发送邮件
     *
     * @param toMail  收件邮箱
     * @param subject 主主题
     * @param message 信息
     * @throws MessagingException 消息异常信息
     * @throws IOException        IO异常信息
     * @throws GlobalException    公共异常信息
     */
    public static void sendMail(List<String> toMail, String subject, String message, String fileName, InputStream inputStream) throws IOException, MessagingException, GlobalException {
        sendMail(toMail, subject, message, new ArrayList<>(), fileName, inputStream);
    }

    /**
     * 发送邮件
     *
     * @param toMail      收件邮箱
     * @param subject     主主题
     * @param message     信息
     * @param ccMail      抄送邮箱
     * @param fileName    文件名
     * @param inputStream 文件数据流
     * @throws MessagingException 消息异常信息
     * @throws IOException        IO异常信息
     * @throws GlobalException    公共异常信息
     */
    public static void sendMail(List<String> toMail, String subject, String message,
                                List<String> ccMail, String fileName, InputStream inputStream) throws MessagingException, IOException, GlobalException {
        if (mailSender == null) {
            throw new GlobalException("未配置邮件信息");
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        final boolean multipart = true;
        // 设置 utf-8 或 GBK 编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, multipart, mailConfig.getEncoding());
        messageHelper.setFrom(mailConfig.getFromMail(), mailConfig.getPersonal());
        messageHelper.setTo(toMail.toArray(new String[toMail.size()]));
        messageHelper.setSubject(subject);
        messageHelper.setText(message, multipart);
        if (StringUtils.isNotNullAndEmpty(fileName) &&
                inputStream != null && inputStream.available() > IntegerConsts.ZERO) {
            messageHelper.addAttachment(fileName, (InputStreamSource) inputStream);
        }
        if (ccMail != null && ccMail.size() > IntegerConsts.ZERO) {
            messageHelper.setCc(ccMail.toArray(new String[ccMail.size()]));
        }

        // 发送邮件
        mailSender.send(mimeMessage);
    }

    /**
     * 创建引用
     */
    public void run() {
        if (StringUtils.isNullAndSpaceOrEmpty(mailConfig.getHost()) ||
                StringUtils.isNullAndSpaceOrEmpty(mailConfig.getFromMail())) {
            return;
        }
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailConfig.getHost());
        mailSender.setPort(mailConfig.getPort());
        mailSender.setPassword(mailConfig.getPassword());
        mailSender.setUsername(mailConfig.getUserName());
        mailSender.setProtocol(mailConfig.getProtocol());
        mailSender.setDefaultEncoding(mailConfig.getEncoding());

        // Properties
        Properties properties = new Properties();
        //使用smtp身份验证
        properties.setProperty("mail.smtp.auth", String.valueOf(mailConfig.isAuth()));
        properties.put("mail.smtp.ssl.enable", mailConfig.isSsl());
        if (mailConfig.isSsl()) {
            //使用SSL，企业邮箱必需！
            //开启安全协议
            MailSSLSocketFactory socketFactory = null;
            try {
                socketFactory = new MailSSLSocketFactory();
                socketFactory.setTrustAllHosts(true);
            } catch (GeneralSecurityException e1) {
                logUtils.error(e1);
            }
            properties.put("mail.smtp.ssl.socketFactory", socketFactory);
        }


        properties.setProperty("mail.smtp.timeout", String.valueOf(mailConfig.getMailTimeout()));
        mailSender.setJavaMailProperties(properties);
    }
}
