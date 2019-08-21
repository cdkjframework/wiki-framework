package com.cdkjframework.util.tool;

import com.cdkjframework.util.log.LogUtils;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.util.tool
 * @ClassName: HostUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class HostUtils {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(HostUtils.class);

    /**
     * 获取主机名
     *
     * @return 返回结果
     */
    public static String getHostName() {
        String hostName = "";
        try {
            InetAddress address = InetAddress.getLocalHost();
            hostName = address.getHostName();
        } catch (Exception ex) {
            logUtil.error(ex);
        }

        //主机名
        return hostName;
    }

    /**
     * 系统类型
     *
     * @return 返回结果
     */
    public static String getOs() {
        String os = "";
        try {
            os = System.getProperty("os.name").toLowerCase();
        } catch (Exception ex) {
            logUtil.error(ex);
        }

        //主机名
        return os;
    }
}
