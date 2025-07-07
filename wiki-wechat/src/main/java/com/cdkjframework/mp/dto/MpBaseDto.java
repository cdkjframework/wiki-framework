package com.cdkjframework.mp.dto;

import lombok.Data;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.mp.dto
 * @ClassName: MpBaseDto
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/1/31 21:37
 * @Version: 1.0
 */
@Data
public class MpBaseDto {

  /**
   * 从全部素材的该偏移位置开始返回，0表示从第一个素材返回
   */
  private Integer offset;
  /**
   * 返回素材的数量，取值在1到20之间
   */
  private Integer count;

  /**
   * 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
   */
  private String type;
}
