package com.cdkjframework.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.config
 * @ClassName: MemberConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2024/4/3 17:48
 * @Version: 1.0
 */
@Data
@Configuration
@EqualsAndHashCode(callSuper = false)
public class AccountConfig extends CustomConfig{
}
