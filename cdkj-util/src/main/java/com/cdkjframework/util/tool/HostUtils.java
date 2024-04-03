package com.cdkjframework.util.tool;

import com.cdkjframework.util.log.LogUtils;
import org.springframework.stereotype.Component;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

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
    String hostName = StringUtils.Empty;
    try {
      InetAddress address = InetAddress.getLocalHost();
      hostName = address.getHostName();
    } catch (Exception ex) {
      logUtil.error(ex.getCause(), ex.getMessage());
    }

    //主机名
    return hostName;
  }

  /**
   * 获取主机IPV6地址
   *
   * @return 返回结果
   */
  public static String getLocalIpv6() {
    return getLocalIp(Boolean.TRUE);
  }

  /**
   * 获取主机IP
   *
   * @return 返回结果
   */
  public static String getLocalHost() {
    return getLocalIp(Boolean.FALSE);
  }

  /**
   * 系统类型
   *
   * @return 返回结果
   */
  public static String getOs() {
    String os = StringUtils.Empty;
    try {
      os = System.getProperty("os.name").toLowerCase();
    } catch (Exception ex) {
      logUtil.error(ex.getCause(), ex.getMessage());
    }

    //主机名
    return os;
  }

  /**
   * 获取主机IPV6地址
   *
   * @param ipv6 是否为IPV6
   * @return 返回结果
   */
  private static String getLocalIp(boolean ipv6) {
    String ip = StringUtils.Empty;
    try {
      // 获取所有网络接口
      Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
      while (interfaces.hasMoreElements()) {
        NetworkInterface networkInterface = interfaces.nextElement();
        // 确保网络接口已启用
        if (networkInterface == null || !networkInterface.isUp() || networkInterface.isLoopback()) {
          continue;
        }
        // 获取与此网络接口绑定的InetAddresses
        Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
        while (addresses.hasMoreElements()) {
          InetAddress inetAddress = addresses.nextElement();
          if (StringUtils.isNotNullAndEmpty(ip) || inetAddress == null) {
            continue;
          }
          boolean isIpv6 = inetAddress instanceof Inet6Address;
          // 检查是否为IPv6地址
          if (ipv6 && isIpv6) {
            ip = inetAddress.getHostAddress();
          } else if (!ipv6 && !isIpv6) {
            ip = inetAddress.getHostAddress();
          }
        }
      }
    } catch (Exception ex) {
      logUtil.error(ex.getCause(), ex.getMessage());
    }

    //主机名
    return ip;
  }
}
