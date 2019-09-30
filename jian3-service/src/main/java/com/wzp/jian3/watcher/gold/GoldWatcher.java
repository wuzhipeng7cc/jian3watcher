package com.wzp.jian3.watcher.gold;

import com.wzp.jian3.common.dto.GoldPriceUnit;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 金价监控接口
 * @author wzp
 */
public interface GoldWatcher {
    /**
     * 获取兑换比例最高的价格
     *
     * @return
     */
    Double getBestPrice()throws Exception;

    /**
     * 获取最合适的金价单元
     *
     * @return
     */
    GoldPriceUnit getBestPriceUnit()throws Exception;

    /**
     * 获取上一个最合适的金价单元
     *
     * @return
     */
    GoldPriceUnit getLastBestPriceUnit() throws Exception;

    /**
     * 获取最近一次的差价
     */
    Double getLastDifference()throws Exception;


    Html getHtml() throws Exception;

    List<GoldPriceUnit> getCurrentList()throws Exception;

}
