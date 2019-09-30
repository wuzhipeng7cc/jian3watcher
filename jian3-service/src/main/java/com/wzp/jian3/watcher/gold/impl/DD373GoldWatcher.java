package com.wzp.jian3.watcher.gold.impl;

import com.wzp.jian3.common.constant.GoldWatcherConstant;
import com.wzp.jian3.rule.gold.DD373Rule;
import com.wzp.jian3.watcher.gold.AbstractGoldWatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.selector.Selectable;

import javax.annotation.PostConstruct;

/**
 * author: wzp
 * date: 2019/10/1
 */
@Service
@Slf4j
public class DD373GoldWatcher extends AbstractGoldWatcher {
    @Autowired
    private DD373Rule dd373Rule;

    @PostConstruct
    private void init() {
        this.setFetchUrl(dd373Rule.getFetchUrl());
        baseGoldRule=dd373Rule;
    }


    @Override
    protected String getPaymentUrlFromSelectable(Selectable selectable) {
        Selectable buy = selectable.xpath(baseGoldRule.getPaymentXpath()).$("a", "href");
        if (StringUtils.isNotBlank(buy.toString())) {
            String substring = buy.toString().substring(2);
            return "https://" + substring;
        }
        return GoldWatcherConstant.ALL_READY_PAY_TXT;
    }

    @Override
    protected Long getInventoryFromSelectable(Selectable selectable) {
        String trim = selectable.toString().trim();
        if(StringUtils.isNotBlank(trim)){
            return Long.valueOf(trim);
        }
        return 0L;
    }

    @Override
    protected String getSellerName(Selectable selectable) {
        return null;
    }
}
