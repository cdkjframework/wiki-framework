package com.cdkjframework.entity.pay.webchat.app;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.entity.pay.webchat.app
 * @ClassName: GoodsBodyEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class GoodsBodyEntity {

    /**
     * 商品ID
     */
    @XStreamAlias("area_code")
    private String goodsId;

    /**
     * 商品微信ID
     */
    @XStreamAlias("wxpay_goods_id")
    private String wxPayGoodsId;

    /**
     * 商品名称
     */
    @XStreamAlias("goods_name")
    private String goodsName;
    /**
     * 商品数量
     */
    private int quantity;

    /**
     * 商品价格
     */
    private int price;

    /**
     * 商品分类
     */
    @XStreamAlias("goods_category")
    private String goodsCategory;

    /**
     * 商品内容
     */
    private String body;
}
