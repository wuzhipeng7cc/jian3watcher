package com.wzp.jian3.watcher.gold;

import com.wzp.jian3.common.dto.GoldPriceUnit;
import com.wzp.jian3.db.mapper.TGoldPriceUnitMapper;
import com.wzp.jian3.db.model.TGoldPriceUnit;
import com.wzp.jian3.rule.gold.BaseGoldRule;
import com.wzp.jian3.util.BeanCopyUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.*;

/**
 * @author wuzhipeng
 * @date 2019-09-3012:35
 */
@Data
@Slf4j
public abstract class AbstractGoldWatcher implements GoldWatcher {

    @Autowired
    protected TGoldPriceUnitMapper tGoldPriceUnitMapper;


    //protected static ThreadLocal<Html> htmlThreadLocal = new ThreadLocal<>();
    /**
     * 可以拿到金价的网页地址
     */
    protected String fetchUrl;

    protected GoldPriceUnit beforeBestPriceUnit = null;

    protected GoldPriceUnit nowBestPriceUnit = null;

    protected BaseGoldRule baseGoldRule;

    @Override
    public Double getBestPrice() throws Exception {
        return nowBestPriceUnit == null ? 0 : nowBestPriceUnit.getPrice();
    }

    @Override
    public GoldPriceUnit getBestPriceUnit() throws Exception {
        return nowBestPriceUnit;
    }

    @Override
    public GoldPriceUnit getLastBestPriceUnit() throws Exception {
        return beforeBestPriceUnit;
    }

    @Override
    public Double getLastDifference() throws Exception {
        return nowBestPriceUnit.getPrice() - beforeBestPriceUnit.getPrice();
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public List<GoldPriceUnit> getCurrentList() throws Exception {
        if(StringUtils.isEmpty(baseGoldRule.getFetchUrl())){
            return new ArrayList<>();
        }
        Date fetchTime = new Date();
        List<GoldPriceUnit> goldPriceUnits = Lists.newArrayList();
        Html html = getHtml();
        Selectable specificArea = html.xpath(baseGoldRule.getSpecificAreaXpath());
        List<Selectable> nodes = specificArea.nodes();
        for (Selectable node : nodes) {
            try {
                GoldPriceUnit goldPriceUnit = new GoldPriceUnit();
                goldPriceUnit.setFetchTime(fetchTime);
                //单价
                Double price = getUnitPrice(node);
                //单价小于三百直接跳过
                if (price <= 300) {
                    continue;
                }
                goldPriceUnit.setPrice(price);
                goldPriceUnit.setPlatform(baseGoldRule.getPlatform());
                /** window.open('/newCreateSingleOrder.aspx?ID=CN20190929170944-80916','_blank'); */
                //获取购买链接
                String paymentUrl = getPaymentUrlFromSelectable(node);
                goldPriceUnit.setPaymentUrl(paymentUrl);
                // 获取库存
                Selectable inventorySc = node.xpath(baseGoldRule.getInventoryXpath());
                Long inventory = getInventoryFromSelectable(inventorySc);
                goldPriceUnit.setInventory(inventory);
                //获取上架时间(目前只适用于贴吧)
                Date addTime = getAddTime(node);
                goldPriceUnit.setAddTime(addTime);
                goldPriceUnits.add(goldPriceUnit);
                //获取卖家姓名
                String sellerName = getSellerName(node);
                goldPriceUnit.setSellerName(sellerName);
                //设置区服
                goldPriceUnit.setArea(baseGoldRule.getArea());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Collections.sort(goldPriceUnits);
        //入库
        fillAttr(goldPriceUnits);
        if (CollectionUtils.isEmpty(goldPriceUnits)) {
            log.error("抓取异常,html:{}\n,area:{}", html.toString(), specificArea.toString());
            throw new Exception("抓取到的文本异常");
        }
        saveDb(goldPriceUnits);
        return goldPriceUnits;
    }

    @Override
    public Html getHtml() throws Exception {
        return getHtmlFromNet(fetchUrl);
    }



    protected void saveDb(List<GoldPriceUnit> goldPriceUnits) {
        tGoldPriceUnitMapper.insertList(BeanCopyUtil.convertList(goldPriceUnits, TGoldPriceUnit.class));
    }

    /**
     * 从文本中提取单价
     *
     * @param text
     * @return
     */
    protected Double getPriceFromText(String text) {
        String price = null;
        try {
            price = text.split("=")[1].replaceAll("金", "");
        } catch (Exception e) {
            log.error("getPriceFromText error,test:{},e", text, e);
            price = "0";
        }
        return Double.valueOf(price);
    }

    protected abstract String getPaymentUrlFromSelectable(Selectable selectable);


    protected abstract Long getInventoryFromSelectable(Selectable selectable);

    protected abstract String getSellerName(Selectable selectable);

    protected Html getHtmlFromNet(String url) throws Exception {
        Map<String, String> header = new HashMap<String, String>();
        header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
        return new Html(Jsoup.connect(url).headers(header).timeout(10000).get().html(), url);
    }

    protected Double getUnitPrice(Selectable selectable) {
        Selectable unitPrices = selectable.xpath(baseGoldRule.getGoldUnitXpath());
        //单价
        return getPriceFromText(unitPrices.toString());
    }

    /**
     * 填充属性
     */
    private void fillAttr(List<GoldPriceUnit> goldPriceUnits) {
        Optional<GoldPriceUnit> first = goldPriceUnits.stream().filter(goldPriceUnit -> goldPriceUnit.getInventory() != 0L).findFirst();
        if (first.isPresent()) {
            beforeBestPriceUnit = nowBestPriceUnit;
            nowBestPriceUnit = first.get();
        }
    }

    /**
     * 获取上架时间
     *
     * @return
     */
    protected Date getAddTime(Selectable selectable) {
        //默认现在
        return new Date();
    }
}
