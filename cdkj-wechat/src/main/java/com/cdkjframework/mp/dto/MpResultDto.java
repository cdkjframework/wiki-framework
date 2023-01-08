package com.cdkjframework.mp.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.mp.dto
 * @ClassName: MpResultDto
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/1/6 21:14
 * @Version: 1.0
 */
@Data
public class MpResultDto {

  /**
   * 数据时间
   */
  private long time;

  /**
   * 访问令牌
   */
  @JSONField(name = "access_token")
  private String accessToken;

  /**
   * 凭证有效时间，单位：秒
   */
  @JSONField(name = "expires_in")
  private Integer expiresIn;

  /**
   * 错误编码
   */
  @JSONField(name = "errcode")
  private Integer errorCode;

  /**
   * 错误消息
   */
  @JSONField(name = "errmsg")
  private String errorMsg;

  /**
   * 上传后的获取标志，长度不固定，但不会超过 128 字符
   */
  @JSONField(name = "media_id")
  private String mediaId;

  /**
   * 草稿的总数
   */
  @JSONField(name = "total_count")
  private Integer totalCount;

  /**
   * 上传后的获取标志，长度不固定，但不会超过 128 字符
   */
  @JSONField(name = "news_item")
  private List<MpDraftDto> newsItem;
}
