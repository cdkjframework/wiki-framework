package com.cdkjframework.mp.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.mp.dto
 * @ClassName: MpMenusDto
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/1/7 11:41
 * @Version: 1.0
 */
@Data
public class MpMenusDto {

  /**
   * 菜单的响应动作类型，
   * view 表示网页类型，
   * click 表示点击类型，
   * miniprogram 表示小程序类型
   *
   * scancode_waitmsg 扫码带提示
   * pic_sysphoto 系统拍照发图
   * scancode_push 扫码推事件
   * pic_photo_or_album 拍照或者相册发图
   * pic_weixin 微信相册发图
   * location_select 发送位置
   * media_id 图片
   * view_limited 图文消息
   * article_id 发布后的图文消息
   * article_view_limited 发布后的图文消息
   */
  private String type;

  /**
   * 菜单标题，不超过16个字节，子菜单不超过60个字节
   */
  private String name;

  /**
   * 菜单 KEY 值，用于消息接口推送，不超过128字节
   */
  private String key;

  /**
   * 网页 链接，用户点击菜单可打开链接，不超过1024字节。 type为 miniprogram 时，不支持小程序的老版本客户端将打开本url。
   */
  private String url;
  /**
   * media_id类型和view_limited类型必须
   * 调用新增永久素材接口返回的合法media_id
   */
  @JSONField(name = "media_id")
  private String mediaId;

  /**
   * miniprogram类型必须
   * 小程序的appid（仅认证公众号可配置）
   */
  @JSONField(name = "appid")
  private String appId;

  /**
   * miniprogram类型必须
   * 小程序的页面路径
   */
  @JSONField(name = "pagepath")
  private String pagePath;
  /**
   * article_id类型和article_view_limited类型必须
   * 发布后获得的合法 article_id
   */
  @JSONField(name = "article_id")
  private String articleId;

  /**
   * 二级菜单数组，个数应为1~5个
   */
  @JSONField(name = "sub_button")
  private List<MpMenusDto> subButton;
}
