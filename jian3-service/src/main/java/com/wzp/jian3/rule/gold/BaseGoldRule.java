package com.wzp.jian3.rule.gold;

import lombok.Data;

/**
 * @author wuzhipeng
 * @date 2019-09-3012:47
 * xpath集合
 */
@Data
public class BaseGoldRule {

    /**
     * 获取网页具体作用域xpath
     */
    private String specificAreaXpath;

    /**
     * 获得金价 xpath
     */
    private String goldUnitXpath;


    /**
     * 获取支付连接 xpath
     */
    private String paymentXpath;

    /**
     * 根目录链接
     */
    private String baseUrl;

    /**
     * 平台
     */
    private String platform;
    /**
     * 具体链接
     */
    private String fetchUrl;
    /**
     * 获取库存规则
     */
    private String inventoryXpath;


    /**
     * 用于取出支付链接正则
     */
    private String paymentRegx;


    /**
     * 用于获取用户姓名
     */
    private String sellerNameXpath;

}
