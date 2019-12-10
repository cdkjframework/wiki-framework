package com.cdkjframework.pay.controller;

import com.cdkjframework.core.controller.WebUiController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.pay.controller
 * @ClassName: PaymentController
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@RestController
@RequestMapping(value = "/cdkj/pay/")
@Api(tags = "支付接口")
public class PaymentController extends WebUiController {

}
