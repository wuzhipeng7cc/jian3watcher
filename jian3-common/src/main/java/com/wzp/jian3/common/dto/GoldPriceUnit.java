package com.wzp.jian3.common.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author wuzhipeng
 * @date 2019-09-3012:24
 * 金价单元
 */
@Data
public class GoldPriceUnit implements Comparable<GoldPriceUnit> {
    /**
     * 平台
     */
    private String platform;

    /**
     * 爬取时间
     */
    private Date fetchTime;

    /**
     * 价格单位元
     */
    private Double price;

    /**
     * 支付连接
     */
    private String paymentUrl;

    /**
     * 库存
     */
    private Long inventory;

    /**
     * 卖家姓名
     */
    private String sellerName;

    /**
     * 上架时间
     */
    private Date addTime;

    /**
     * 区服
     */
    private String area;

    @Override
    public int compareTo(GoldPriceUnit o) {
        return this.getPrice() > o.getPrice() ? -1 : 1;
    }
}
