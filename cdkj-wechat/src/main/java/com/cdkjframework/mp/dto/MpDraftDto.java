package com.cdkjframework.mp.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.mp.dto
 * @ClassName: MpDraftDto
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/1/7 16:55
 * @Version: 1.0
 */
@Data
public class MpDraftDto {

  /**
   * 标题
   */
  private String title;
  /**
   * 作者
   */
  private String author;
  /**
   * 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空。如果本字段为没有填写，则默认抓取正文前54个字。
   */
  private String digest;
  /**
   * 图文消息的具体内容，支持 HTML 标签，必须少于2万字符，小于1M，且此处会去除 JS ,涉及图片 url 必须来源 "上传图文消息内的图片获取URL"接口获取。外部图片 url 将被过滤。
   */
  private String content;
  /**
   * 图文消息的原文地址，即点击“阅读原文”后的URL
   */
  @JSONField(name = "content_source_url")
  private String contentSourceUrl;
  /**
   * 图文消息的封面图片素材id（必须是永久MediaID）
   */
  @JSONField(name = "thumb_media_id")
  private String thumbMediaId;
  /**
   * Uint32 是否打开评论，0不打开(默认)，1打开
   */
  @JSONField(name = "need_open_comment")
  private Integer needOpenComment;
  /**
   * Uint32 是否粉丝才可评论，0所有人可评论(默认)，1粉丝才可评论
   */
  @JSONField(name = "only_fans_can_comment")
  private Integer onlyFansCanComment;

  /**
   * 上传后的获取标志，长度不固定，但不会超过 128 字符
   */
  @JSONField(name = "media_id")
  private String mediaId;

  /**
   * 草稿的临时链接
   */
  private String url;

  /**
   * 要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
   */
  private Integer index;

  /**
   * 文章
   */
  private MpDraftDto articles;

  /**
   * 从全部素材的该偏移位置开始返回，0表示从第一个素材返回
   */
  private Integer offset;
  /**
   * 返回素材的数量，取值在1到20之间
   */
  private Integer count;
  /**
   * 1 表示不返回 content 字段，0 表示正常返回，默认为 0
   */
  @JSONField(name = "no_content")
  private Integer noContent;

  /**
   * 发布
   */
  private Integer publish;
}
