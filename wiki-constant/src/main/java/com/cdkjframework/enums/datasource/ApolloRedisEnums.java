package com.cdkjframework.enums.datasource;


import com.cdkjframework.enums.basics.BasicsEnum;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.enums.datasource
 * @ClassName: ApolloRedisEnum
 * @Description: redis
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum ApolloRedisEnums implements BasicsEnum {

  /**
   * 数据库
   */
  database {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "spring.data.redis.database";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "database";
    }
  },

  /**
   * 主机
   */
  host {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "spring.data.redis.host";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "host";
    }
  },

  /**
   * 端口
   */
  port {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "spring.data.redis.port";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "port";
    }
  },
  /**
   * 密码
   */
  password {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "spring.data.redis.password";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "password";
    }
  },
  /**
   * 最大连接数
   */
  maxActive {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "spring.data.redis.maxActive";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "maxActive";
    }
  },
  /**
   * 超时时间
   */
  timeOut {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "spring.data.redis.timeOut";
    }

    /**
     * 获取枚举名称
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
      return "port";
    }
  },
  /**
   * 最大空闲连接数
   */
  maxIdle {
    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
      return "spring.data.redis.maxIdle";
    }

        /**
         * 获取枚举名称
         *
         * @return 返回结果
         */
        @Override
        public String getCode() {
            return "maxIdle";
        }
    };
}
