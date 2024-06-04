package com.cdkjframework.security.controller;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.BusinessConsts;
import com.cdkjframework.constant.CacheConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.user.security.SecurityUserEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.security.service.UserAuthenticationService;
import com.cdkjframework.util.encrypts.AesUtils;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.files.images.code.QrCodeUtils;
import com.cdkjframework.util.make.VerifyCodeUtils;
import com.cdkjframework.util.network.http.HttpRequestUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.cdkjframework.constant.BusinessConsts.TICKET_SUFFIX;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.controller
 * @ClassName: SecurityCertificateController
 * @Description: 安全认证接口
 * @Author: xiaLin
 * @Version: 1.0
 */
@RestController
@Api(tags = "安全认证接口")
@RequiredArgsConstructor
@RequestMapping(value = "/security")
public class SecurityCertificateController {

  /**
   * 二维生成
   */
  private final QrCodeUtils qrCodeUtils;

  /**
   * 配置文件
   */
  private final CustomConfig customConfig;

  /**
   * 用户权限服务
   */
  private final UserAuthenticationService userAuthenticationServiceImpl;

  /**
   * 获取验证码
   *
   * @param request  请求
   * @param response 响应
   * @throws IOException IO异常信息
   */
  @GetMapping(value = "/verify/code")
  @ApiOperation(value = "获取验证码")
  public void verificationCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
    OutputStream outputStream = response.getOutputStream();
    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);

    // 创建 session
    HttpSession session = request.getSession();
    // 生成验证码
    String code = VerifyCodeUtils.outputVerifyImage(IntegerConsts.ONE_HUNDRED, IntegerConsts.THIRTY_FIVE,
            outputStream, IntegerConsts.FOUR);
    // 将图形验证码存入到session中
    session.setAttribute(BusinessConsts.IMAGE_CODE, code);
    session.setAttribute(BusinessConsts.TIME, System.currentTimeMillis());
  }

  /**
   * 获取扫码二维码
   *
   * @param request  请求
   * @param response 响应
   * @throws IOException IO异常信息
   */
  @ApiOperation(value = "获取验证码")
  @GetMapping(value = "/scan/qrcode.html")
  public void scanCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    OutputStream outputStream = response.getOutputStream();
    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);

    // 创建 session
    HttpSession session = request.getSession();
    String id = session.getId().toLowerCase();
    long currentTime = System.currentTimeMillis();
    StringBuffer content = new StringBuffer(id);
    content.append(StringUtils.COMMA);
    content.append(currentTime);
    // 生成二维码
    if (StringUtils.isNotNullAndEmpty(customConfig.getQrlogo())) {
      // 添加LOGO
      InputStream stream = HttpRequestUtils.readImages(customConfig.getQrlogo());
      qrCodeUtils.createQrCode(content.toString(), stream, outputStream);
    } else {
      qrCodeUtils.createQrCode(content.toString(), outputStream);
    }
    String statusKey = CacheConsts.USER_PREFIX + BusinessConsts.STATUS;
    String timeKey = CacheConsts.USER_PREFIX + BusinessConsts.TIME;
    // 将图形验证码存入到session中
    RedisUtils.hSet(timeKey, id, String.valueOf(currentTime));
    RedisUtils.hSet(statusKey, id, StringUtils.ZERO);
  }

  /**
   * 验证二维码是否已被扫码
   *
   * @param request  请求
   * @param response 响应
   * @throws IOException IO异常信息
   */
  @ResponseBody
  @ApiOperation(value = "验证二维码是否已被扫码")
  @GetMapping(value = "/validate.html")
  public ResponseBuilder validate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // 创建 session
    HttpSession session = request.getSession();
    String id = session.getId().toLowerCase();
    String statusKey = CacheConsts.USER_PREFIX + BusinessConsts.STATUS;
    String timeKey = CacheConsts.USER_PREFIX + BusinessConsts.TIME;

    // 读取状态
    Integer status = ConvertUtils.convertInt(RedisUtils.hGet(statusKey, id));
    long time = ConvertUtils.convertLong(RedisUtils.hGet(timeKey, id));

    // 返回结果
    String message;
    switch (status) {
      case 1:
        long currentTime = System.currentTimeMillis();
        long value = ((currentTime - time) / IntegerConsts.ONE_THOUSAND) / IntegerConsts.SIXTY;
        message = "success";
        if (value > IntegerConsts.FIVE) {
          message = "timeout";
        }
        break;
      case 2:
        String tokenKey = CacheConsts.USER_PREFIX + BusinessConsts.HEADER_TOKEN + StringUtils.HORIZONTAL + id;
        String token = RedisUtils.syncGet(tokenKey);
        if (StringUtils.isNotNullAndEmpty(token)) {
          message = "successful";
          Claims claims = JwtUtils.parseJwt(token, customConfig.getJwtKey());
          // 生成 ticket 票据
          token = ConvertUtils.convertString(claims.get(BusinessConsts.HEADER_TOKEN));
          String ticket = URLEncoder.encode(AesUtils.base64Encode(token), StandardCharsets.UTF_8.toString()) + TICKET_SUFFIX;
          response.setHeader(BusinessConsts.TICKET, ticket);
          String ticketKey = CacheConsts.USER_PREFIX + BusinessConsts.HEADER_TOKEN;
          RedisUtils.hSet(ticketKey, token, ticket);
        } else {
          message = "timeout";
        }
        break;
      default:
        message = StringUtils.Empty;
        break;
    }
    // 返回结果
    return ResponseBuilder.successBuilder(message);
  }

  /**
   * 票据认证
   *
   * @throws IOException IO异常信息
   */
  @ResponseBody
  @ApiOperation(value = "票据认证")
  @GetMapping(value = "/ticket.html")
  public SecurityUserEntity ticket(@RequestParam("ticket") String ticket, HttpServletResponse response) throws Exception {
    return userAuthenticationServiceImpl.ticket(ticket, response);
  }

  /**
   * 票据认证
   *
   * @param request 响应
   * @return 返回票据信息
   * @throws UnsupportedEncodingException 异常信息
   * @throws GlobalException              异常信息
   */
  @ResponseBody
  @ApiOperation(value = "票据认证")
  @GetMapping(value = "/refresh/ticket.html")
  public ResponseBuilder refreshTicket(HttpServletRequest request) throws UnsupportedEncodingException, GlobalException {
    String ticket = userAuthenticationServiceImpl.refreshTicket(request);
    // 返回结果
    return ResponseBuilder.successBuilder(ticket);
  }

  /**
   * 扫码确认接口
   *
   * @throws IOException IO异常信息
   */
  @ResponseBody
  @ApiOperation(value = "验证二维码是否已被扫码")
  @GetMapping(value = "/confirm.html")
  public void confirm(@RequestParam("id") String id) throws Exception {
    String statusKey = CacheConsts.USER_PREFIX + BusinessConsts.STATUS;
    RedisUtils.hSet(statusKey, id, String.valueOf(IntegerConsts.ONE));
  }

  /**
   * 扫码完成接口
   *
   * @param username 用户名
   * @param id       ID
   * @throws IOException IO异常信息
   */
  @ResponseBody
  @ApiOperation(value = "扫码完成接口【即登录】")
  @PostMapping(value = "/completed.html")
  public void completed(@RequestParam("username") String username, @RequestParam("id") String id) throws Exception {

    String statusKey = CacheConsts.USER_PREFIX + BusinessConsts.STATUS;
    Integer status = ConvertUtils.convertInt(RedisUtils.hGet(statusKey, id));
    if (!status.equals(IntegerConsts.ONE)) {
      throw new GlobalException("二维码已过期，请刷新重试！");
    }
    RedisUtils.syncDel(statusKey);

    // 受权信息
    userAuthenticationServiceImpl.authenticate(username, id);
    RedisUtils.hSet(statusKey, id, String.valueOf(IntegerConsts.TWO));
  }

  /**
   * 用户退出登录
   *
   * @param request 响应
   * @throws GlobalException 异常信息
   */
  @ResponseBody
  @ApiOperation(value = "扫码完成接口【即登录】")
  @PostMapping(value = "/logout.html")
  public void logout(HttpServletRequest request) throws GlobalException {
    userAuthenticationServiceImpl.logout(request);
  }
}
