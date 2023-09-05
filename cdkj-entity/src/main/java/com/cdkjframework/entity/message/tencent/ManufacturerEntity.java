package com.cdkjframework.entity.message.tencent;

import lombok.Data;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.message.tencent
 * @ClassName: ManufacturerEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/9/5 15:58
 * @Version: 1.0
 */
@Data
public class ManufacturerEntity {

  /**
   * 文件名称
   */
  private String fileName = "xg_ring";

  /**
   * 工厂类型  xm hw oppo vivo
   */
  private String factoryType;

  /**
   * 服务商ID
   */
  private String chId;

  /**
   * 工厂ID
   */
  private String factoryChId;
}
