package com.wzp.jian3.test;

import org.jsoup.Jsoup;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class GithubRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    @Override
    public void process(Page page) {
        Selectable xpath = page.getHtml().xpath("//*[@id=\"divCommodityLst\"]/ul/li[4]/h6/span[1]");
        page.putField("goldPrice", xpath.toString());
        if (page.getResultItems().get("name")==null) {
            //skip this page
            page.setSkip(true);
        }
        System.out.println(xpath.toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws IOException {
        Html html = new Html(Jsoup.parse(new URL("http://www.uu898.com/newTrade.aspx?gm=150&area=1319&srv=8963&c=-3"), 3000).html(), "http://www.uu898.com/newTrade.aspx?gm=150&area=1319&srv=8963&c=-3");
        Selectable xpath = html.xpath("//*[@id=\"divCommodityLst\"]/ul/li[4]/h6/span[1]/text()");
        List<String> all = xpath.all();
        System.out.println(all.toString());
    }
}