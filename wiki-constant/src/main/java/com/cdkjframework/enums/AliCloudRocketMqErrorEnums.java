package com.cdkjframework.enums;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.enums
 * @ClassName: AliCloudRocketMqErrorEnum
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum AliCloudRocketMqErrorEnums {

  /**
   * 系统错误
   */
  ONS_SYSTEM_ERROR {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "ONS_SYSTEM_ERROR";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "MQ 后端异常";
    }
  },
  /**
   * 服务不支持
   */
  ONS_SERVICE_UNSUPPORTED {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "ONS_SERVICE_UNSUPPORTED";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "当前调用在对应的 Region 区域不支持";
    }
  },
  /**
   * ONS调用错误
   */
  ONS_INVOKE_ERROR {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "ONS_INVOKE_ERROR";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "Open API 接口调用失败";
    }
  },
  /**
   * 业务字段检查无效
   */
  BIZ_FIELD_CHECK_INVALID {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BIZ_FIELD_CHECK_INVALID";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "参数检验失败";
    }
  },

  /**
   * 业务主题不存在
   */
  BIZ_TOPIC_NOT_FOUND {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BIZ_TOPIC_NOT_FOUND";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "Topic 没有找到";
    }
  },
  /**
   * 业务订阅不存在
   */
  BIZ_SUBSCRIPTION_NOT_FOUND {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BIZ_SUBSCRIPTION_NOT_FOUND";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "目标订阅关系 CID 找不到";
    }
  },
  /**
   * 业务订阅已存在
   */
  BIZ_PUBLISHER_EXISTED {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BIZ_PUBLISHER_EXISTED";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "指定 PID 已经存在";
    }
  },
  /**
   * 业务订阅已存在
   */
  BIZ_SUBSCRIPTION_EXISTED {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BIZ_SUBSCRIPTION_EXISTED";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "指定 CID 已经存在";
    }
  },
  /**
   * 商务_消费者_非在线
   */
  BIZ_CONSUMER_NOT_ONLINE {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BIZ_CONSUMER_NOT_ONLINE";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "指定 CID 的客户端不在线";
    }
  },
  /**
   * 商务_无消息
   */
  BIZ_NO_MESSAGE {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BIZ_NO_MESSAGE";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "当前查询条件没有匹配消息";
    }
  },
  /**
   * 商务_无区域
   */
  BIZ_REGION_NOT_FOUND {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BIZ_REGION_NOT_FOUND";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "请求的 Region 找不到";
    }
  },
  /**
   * 商务_主题已存在
   */
  BIZ_TOPIC_EXISTED {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BIZ_TOPIC_EXISTED";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "指定 Topic 已经存在";
    }
  },
  /**
   * 商务_指定 ProducerId 已经被其他用户使用
   */
  BIZ_PRODUCER_ID_BELONG_TO_OTHER_USER {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BIZ_PRODUCER_ID_BELONG_TO_OTHER_USER";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "当前 PID 已经被其他用户占用";
    }
  },
  /**
   * 当前 CID 已经被其他用户占用
   */
  BIZ_CONSUMER_ID_BELONG_TO_OTHER_USER {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BIZ_CONSUMER_ID_BELONG_TO_OTHER_USER";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "当前 CID 已经被其他用户占用";
    }
  },
  /**
   * 获取枚举值
   */
  BIZ_PUBLISH_INFO_NOT_FOUND {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "BIZ_PUBLISH_INFO_NOT_FOUND";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "请求的 PID 没有找到";
    }
  },
  /**
   * 获取枚举值
   */
  EMPOWER_EXIST_ERROR {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "EMPOWER_EXIST_ERROR";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "当前授权关系已经存在";
    }
  },
  /**
   * 获取枚举值
   */
  EMPOWER_OWNER_CHECK_ERROR {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "EMPOWER_OWNER_CHECK_ERROR";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getName() {
      return "当前用户不是授权 Topic 的 Owner";
    }
  };

  /**
   * 获取枚举值
   *
   * @return 返回结果
   */
  public abstract String getValue();

  /**
   * 获取枚举名称
   *
   * @return 返回结果
   */
  public abstract String getName();
}
