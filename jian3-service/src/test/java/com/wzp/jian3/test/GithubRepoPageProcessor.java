package com.wzp.jian3.test;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//        Selectable xpath = html.xpath("//*[@id=\"divCommodityLst\"]/ul/li[4]/h6/span[1]/text()");
        Selectable xpath = html.xpath("//*[@id=\"divCommodityLst\"]/ul");
        List<Selectable> nodes = xpath.nodes();
        for (Selectable node : nodes) {

            Selectable xpath2 = node.xpath("//li[2]/div/text()");
            System.out.println(xpath2);
//            Element body = Jsoup.parse(xpath1.toString()).body();
//            String attr = body.select("a").attr("onclick");
//            System.out.println(xpath1);
        }
        System.out.println(xpath.getClass());
    }

    @Test
    public void reg() {
        String test = "window.open('/newCreateSingleOrder.aspx?ID=CN20190929134921-71828','_blank');";
        Matcher matcher = Pattern.compile("(?<=(?:\\(')).{1,200}(?=(?:',))").matcher(test);
        //Matcher matcher = Pattern.compile("window").matcher(test);
        while (matcher.find()) {
            System.out.println(matcher.group());

        }
    }

    @Test
    public void reg2() {
        String test = " 10 免费兑换此商品 \n";
        Matcher matcher = Pattern.compile("(\\d*)").matcher(test);
        while (matcher.find()) {
            System.out.println(matcher.group());

        }
    }

    @Test
    public void test373() throws Exception {
        String url = "https://www.dd373.com/s/8v8pc2-hswdmk-kqkv28-0-0-0-cwmaee-0-0-0-0-0-0-0-0.html";
        Html html = new Html(Jsoup.parse(new URL(url), 3000).html(), url);
        Selectable xpath = html.xpath("//div[@id=\"biz_content_1\"]/div[@class=\"box money_ner\"]");
        List<Selectable> nodes = xpath.nodes();
        for (Selectable node : nodes) {
            //库存
            Selectable xpath1 = node.xpath("//div[@class=\"num left\"]/text()");

            System.out.println(xpath1);
            //价格库存
            Selectable xpath2 = node.xpath("//div[@class=\"dan left\"]/div[1]/p[1]/text()");
            System.out.println("price" + xpath2);
            //购买链接
            Selectable buy = node.xpath("//div[@class=\"dan left\"]/div[2]/a[1]").$("a", "href");
            if (StringUtils.isNotBlank(buy.toString())) {
                String substring = buy.toString().substring(2);
                String a = "https://" + substring;
                System.out.println(a);
            }
            System.out.println("=====");
        }
    }

    @Test
    public void tiebaTest() throws Exception{
        String fetchUrl="https://tieba.baidu.com/p/6283046934";
            Map<String, String> header = new HashMap<String, String>();
            header.put("User-Agent", "  Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0");
            header.put("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            header.put("Accept-Language", "zh-cn,zh;q=0.5");
            header.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
            Html html = new Html(Jsoup.connect(fetchUrl).headers(header).timeout(10000).get().html(), fetchUrl);
            //获取最后一页地址
        Selectable xpath3 = html.xpath("//li[@class=\"l_pager pager_theme_4 pb_list_pager\"]/a");
        List<Selectable> linkNodes = xpath3.links().nodes();
        //Selectable getLastPageUrl = xpath3;
        //.$("a", "href");
        String s1 = linkNodes.get(linkNodes.size()-1).toString();
        System.out.println("last:"+s1);
        //Selectable xpath = html.xpath("//div[@id=\"j_p_postlist\"]//div[@class=\"d_post_content j_d_post_content \"]");
        Selectable xpath = html.xpath("//div[@id=\"j_p_postlist\"]//div[@class=\"l_post l_post_bright j_l_post clearfix  \"]");
        List<Selectable> nodes = xpath.nodes();
        int idx=1;
        for (Selectable node : nodes)
        {
            //获取发帖时间
            Selectable time = node.xpath("//div[@class=\"post-tail-wrap\"]/span[4]/text()");
            System.out.println("time"+time.toString());
            String contentXpath = "//div[@class=\"post_bubble_middle_inner\"]";
            //获取姓名
            String s = node.xpath("//li[@class=\"d_name\"]/a/text()").toString();
            System.out.println(s);
//            Selectable node = nodes.get(i);
            Selectable xpath1 = node.xpath(contentXpath);
            int size = xpath1.nodes().size();
            if(size>0){
                Selectable xpath2 = xpath1.xpath("div/text()");
                System.out.println(xpath2);
            }
            else {

                Selectable xpath2 = node.xpath("//div[@class=\"d_post_content j_d_post_content \"]/text()");
                System.out.println(xpath2.toString());
            }
            //直接取评论内容
            //尝试另一种方式

            System.out.println("========"+idx+"=======");
            idx++;
//            String s = node.xpath(contentXpath).toString();
//            System.out.println(s);
        }

    }

    private  Double getPriceFromText(String text) {
        Double price= 0d;
        if(StringUtils.isBlank(text)){
            return price;
        }
        if (text.contains("出")){
            String regx="(\\d{3})";
            Matcher matcher = Pattern.compile(regx).matcher(text);
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
    @Test
    public void testTB(){
        String text=" 480出300r，要的留言";
        System.out.println(getPriceFromText(text));
    }

}