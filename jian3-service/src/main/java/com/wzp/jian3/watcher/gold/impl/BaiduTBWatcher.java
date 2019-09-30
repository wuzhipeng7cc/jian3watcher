package com.wzp.jian3.watcher.gold.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.wzp.jian3.common.dto.GoldPriceUnit;
import com.wzp.jian3.db.model.TGoldPriceUnit;
import com.wzp.jian3.rule.gold.BaiduRule;
import com.wzp.jian3.rule.gold.UU898Rule;
import com.wzp.jian3.util.BeanCopyUtil;
import com.wzp.jian3.watcher.gold.AbstractGoldWatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import javax.annotation.PostConstruct;
import javax.xml.soap.Node;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wuzhipeng
 * @date 2019/10/37:47 AM
 */
@Service
@Slf4j
public class BaiduTBWatcher extends AbstractGoldWatcher {

    @Autowired
    private BaiduRule baiduRuleu;

    @PostConstruct
    private void init() {
        this.setFetchUrl(baiduRuleu.getFetchUrl());
        baseGoldRule=baiduRuleu;
    }


   private Pattern compile = Pattern.compile("(\\d{3})");

    @Override
    protected String getPaymentUrlFromSelectable(Selectable selectable) {
        return "";
    }

    @Override
    protected Long getInventoryFromSelectable(Selectable selectable) {
        return 1L;
    }

    @Override
    protected String getSellerName(Selectable selectable) {
        Selectable xpath = selectable.xpath(baseGoldRule.getSellerNameXpath());
        return xpath.toString();
    }

    @Override
    protected Double getPriceFromText(String text) {
        Double price= 0d;
        if(StringUtils.isBlank(text)){
            return price;
        }
        if (text.contains("出")){
            Matcher matcher = compile.matcher(text);
            while (matcher.find()){
                String group = matcher.group();
                Double aDouble = Double.valueOf(group);
                if(600>aDouble&&aDouble>450){
                    price=Double.valueOf(group);
                }
            }
        }
        return price;
    }

    @Override
    protected Double getUnitPrice(Selectable node) {
        String text = "";
        String contentXpath = "//div[@class=\"post_bubble_middle_inner\"]";
        Selectable content = node.xpath(contentXpath);
        int size = content.nodes().size();
        if (size > 0) {
            Selectable xpath = content.xpath("div/text()");
            text = xpath.toString();
        } else {
            Selectable xpath = node.xpath("//div[@class=\"d_post_content j_d_post_content \"]/text()");
            text=xpath.toString();
        }
        return getPriceFromText(text);
    }

    @Override
    public Html getHtml() throws Exception {
        //获取最后一页地址
        //a标签集合
        Selectable as = super.getHtml().xpath("//li[@class=\"l_pager pager_theme_4 pb_list_pager\"]/a");
        List<Selectable> linkNodes = as.links().nodes();
        //最后一页
        String lastLink = linkNodes.get(linkNodes.size()-1).toString();
        log.info("lastLink: now is :{}",lastLink);
        fetchUrl=lastLink;
        return super.getHtml();
    }

    @Override
    protected Date getAddTime(Selectable selectable) {
        Selectable time = selectable.xpath("//div[@class=\"post-tail-wrap\"]/span[4]/text()");

        String timeStr = time.toString();
        //有时候时间在第三个
        if(StringUtils.isBlank(timeStr)){
            timeStr=selectable.xpath("//div[@class=\"post-tail-wrap\"]/span[3]/text()").toString();
        }
        DateTime parse = DateUtil.parse(timeStr);
        return parse.toJdkDate();
    }

    @Override
    protected void saveDb(List<GoldPriceUnit> goldPriceUnits) {
        //由于百度不会刷新状态本质上是一种评论
        for (GoldPriceUnit goldPriceUnit : goldPriceUnits) {
            //先查是否已经插入过,筛选重复数据
            Example example = new Example(TGoldPriceUnit.class);
            example.createCriteria().andEqualTo("addTime",goldPriceUnit.getAddTime()).andEqualTo("sellerName",goldPriceUnit.getSellerName());
            List<TGoldPriceUnit> tGoldPriceUnits = tGoldPriceUnitMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(tGoldPriceUnits)){
                TGoldPriceUnit t = BeanCopyUtil.objConvert(goldPriceUnit, TGoldPriceUnit.class);
                tGoldPriceUnitMapper.insertSelective(t);
                log.info("name:{},time:{}贴吧数据插入成功",goldPriceUnit.getSellerName(),goldPriceUnit.getAddTime());
            }
            else {
                log.warn("name:{},time:{}数据已经插入过",goldPriceUnit.getSellerName(),goldPriceUnit.getAddTime());
            }

        }
    }
}
