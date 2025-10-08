package com.cdkjframework.oauth2.controller;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.oauth2.entity.TokenResponse;
import com.cdkjframework.oauth2.service.CustomOauth2AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.controller
 * @ClassName: Oauth2AuthorizationController
 * @Description: oauth2授权控制器
 * @Author: xiaLin
 * @Date: 2025/7/31 13:31
 * @Version: 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/oauth")
public class Oauth2AuthorizationController {

  /**
   * oauth2授权服务
   */
  private final CustomOauth2AuthorizationService customOauth2AuthorizationService;

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
    return customOauth2AuthorizationService.authorizationCode(clientId, responseType, scope);
  }

  /**
   * 获取访问令牌
   *
   * @param grantType    授权类型
   * @param clientId     客户端ID
   * @param code         授权码
   * @param timestamp    时间戳
   * @param signature    签名
   * @param refreshToken 刷新令牌
   * @return 访问令牌
   */
  @ResponseBody
  @GetMapping("/token")
  public TokenResponse token(@RequestParam("grant_type") String grantType,
                             @RequestParam(name = "client_id", required = false) String clientId,
                             @RequestParam(name = "code", required = false) String code,
                             @RequestParam(name = "timestamp", required = false) String timestamp,
                             @RequestParam(name = "refresh_token", required = false) String refreshToken,
                             @RequestParam(name = "signature", required = false) String signature) {
    return customOauth2AuthorizationService.token(grantType, clientId, code, timestamp, refreshToken, signature);
  }
}
