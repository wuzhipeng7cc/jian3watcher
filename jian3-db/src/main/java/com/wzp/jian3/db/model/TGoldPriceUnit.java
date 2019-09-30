package com.wzp.jian3.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_gold_price_unit")
@NoArgsConstructor
@Data
public class TGoldPriceUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 平台名称
     */
    private String platform;

    @Column(name = "fetch_time")
    private Date fetchTime;

    private BigDecimal price;

    /**
     * 支付链接
     */
    @Column(name = "payment_url")
    private String paymentUrl;

    /**
     * 库存
     */
    private Long inventory;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "last_modify_time")
    private Date lastModifyTime;

    /**
     * 卖家姓名
     */
    @Column(name = "seller_name")
    private String sellerName;

    /**
     * 上架时间
     */
    @Column(name = "add_time")
    private Date addTime;

    public TGoldPriceUnit(Long id, String platform, Date fetchTime, BigDecimal price, String paymentUrl, Long inventory, Date createdTime, Date lastModifyTime, String sellerName, Date addTime) {
        this.id = id;
        this.platform = platform;
        this.fetchTime = fetchTime;
        this.price = price;
        this.paymentUrl = paymentUrl;
        this.inventory = inventory;
        this.createdTime = createdTime;
        this.lastModifyTime = lastModifyTime;
        this.sellerName = sellerName;
        this.addTime = addTime;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 获取平台名称
     *
     * @return platform - 平台名称
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * @return fetch_time
     */
    public Date getFetchTime() {
        return fetchTime;
    }

    /**
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 获取支付链接
     *
     * @return payment_url - 支付链接
     */
    public String getPaymentUrl() {
        return paymentUrl;
    }

    /**
     * 获取库存
     *
     * @return inventory - 库存
     */
    public Long getInventory() {
        return inventory;
    }

    /**
     * @return created_time
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * @return last_modify_time
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * 获取卖家姓名
     *
     * @return seller_name - 卖家姓名
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * 获取上架时间
     *
     * @return add_time - 上架时间
     */
    public Date getAddTime() {
        return addTime;
    }
}