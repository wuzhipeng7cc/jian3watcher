package com.wzp.jian3.watcher.gold.impl;

import com.wzp.jian3.common.constant.GoldWatcherConstant;
import com.wzp.jian3.rule.gold.UU898Rule;
import com.wzp.jian3.watcher.gold.AbstractGoldWatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.selector.Selectable;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wuzhipeng
 * @date 2019-09-3012:39
 */
@Service
@Slf4j
public class UU898GoldWatcher extends AbstractGoldWatcher {

    private static Pattern compile;
    private static Pattern digitalPattern = Pattern.compile("(\\d*)");

    @Autowired
    private UU898Rule uu898Rule;

    @PostConstruct
    private void init() {
        this.setFetchUrl(uu898Rule.getFetchUrl());
        compile=Pattern.compile(uu898Rule.getPaymentRegx());
        baseGoldRule=uu898Rule;
    }






    @Override
    protected String getPaymentUrlFromSelectable(Selectable selectable) {
    String rawText = selectable.xpath(uu898Rule.getPaymentXpath()).$("a", "onclick").get();
        if (StringUtils.isNotBlank(rawText)) {
            Matcher matcher = compile.matcher(rawText);
            while (matcher.find()) {
                String group = matcher.group();
                return uu898Rule.getBaseUrl() + group;
            }
        } else {
            return GoldWatcherConstant.ALL_READY_PAY_TXT;
        }
        return GoldWatcherConstant.ALL_READY_PAY_TXT;
    }

    @Override
    protected Long getInventoryFromSelectable(Selectable selectable) {
        Long inventory=0L;
        Matcher matcher = digitalPattern.matcher(selectable.toString().trim());
        if (matcher.find()) {
            String group = matcher.group();
            if (!StringUtils.isBlank(group)) {
                inventory=Long.valueOf(group.trim());
            }
        }
        return inventory;
    }

    @Override
    protected String getSellerName(Selectable selectable) {
        return null;
    }


}
