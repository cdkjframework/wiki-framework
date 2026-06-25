package com.cdkjframework.oauth2.constant;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.oauth2.constant
 * @ClassName: TokenMetricsKeys
 * @Description: Token Metrics Keys 常量
 * @Author: xiaLin
 * @Date: 2025/9/4 22:06
 * @Version: 1.0
 */
public interface TokenMetricsKeys {

  /**
   * 私有构造函数，防止实例化
   */
  String START_TIME = "token_metrics_start";

  /**
   * 客户端ID
   */
  String CLIENT_ID = "token_metrics_client_id";

  /**
   * 授权类型
   */
  String GRANT_TYPE = "token_metrics_grant_type";

  /**
   * 远程IP
   */
  String REMOTE_IP = "token_metrics_remote_ip";

  /**
   * 用户代理
   */
  String USER_AGENT = "token_metrics_user_agent";
}