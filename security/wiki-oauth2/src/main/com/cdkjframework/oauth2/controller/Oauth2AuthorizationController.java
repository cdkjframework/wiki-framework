package com.cdkjframework.oauth2.controller;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.oauth2.entity.RefreshToken;
import com.cdkjframework.oauth2.service.Oauth2AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: cdjsyun-tool
 * @Package: com.cdkjframework.oauth2.controller
 * @ClassName: Oauth2AuthorizationController
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/7/31 13:31
 * @Version: 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/oauth2")
public class Oauth2AuthorizationController {

  /**
   * oauth2授权服务
   */
  private final Oauth2AuthorizationService oauth2AuthorizationService;

  /**
   * 授权端点
   *
   * @param clientId     客户端ID
   * @param responseType 响应类型
   * @param scope        授权范围
   * @return 授权页面或信息
   */
  @ResponseBody
  @GetMapping("/authorize")
  public ResponseBuilder authorizationCode(@RequestParam("client_id") String clientId,
                                           @RequestParam("response_type") String responseType,
                                           @RequestParam("scope") String scope) {
    return oauth2AuthorizationService.authorizationCode(clientId, responseType, scope);
  }

  /**
   * 获取访问令牌
   *
   * @param code      授权码
   * @param timestamp 时间戳
   * @param clientId  客户端ID
   * @param signature 签名
   * @param grantType 授权类型
   * @return 访问令牌
   */
  @ResponseBody
  @GetMapping("/access_token")
  public ResponseBuilder accessToken(@RequestParam("code") String code,
                                     @RequestParam("timestamp") String timestamp,
                                     @RequestParam("client_id") String clientId,
                                     @RequestParam("signature") String signature,
                                     @RequestParam("grant_type") String grantType) {
    return oauth2AuthorizationService.accessToken(code, timestamp, signature, clientId, grantType);
  }

  /**
   * 刷新令牌
   *
   * @param refreshToken 刷新令牌
   * @return 新的访问令牌
   */
  @ResponseBody
  @PostMapping("/refresh_token")
  public ResponseBuilder refreshToken(@RequestBody RefreshToken refreshToken) {
    return oauth2AuthorizationService.refreshToken(refreshToken.getRefreshToken());
  }
}
