package com.wzp.jian3.controller.watcher;

import com.wzp.jian3.common.dto.CommonResult;
import com.wzp.jian3.common.dto.GoldPriceUnit;
import com.wzp.jian3.watcher.gold.GoldWatcher;
import com.wzp.jian3.watcher.gold.impl.BaiduTBWatcher;
import com.wzp.jian3.watcher.gold.impl.DD373GoldWatcher;
import com.wzp.jian3.watcher.gold.impl.UU898GoldWatcher;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wuzhipeng
 * @date 2019-09-3015:18
 */
@RestController
@RequestMapping("/jian3/goldWatcher")
@RefreshScope
public class GoldWatcherController {

    @Autowired
    private ApplicationContext applicationContext;

    @ApiOperation("获取uu898当前的金价")
    @GetMapping("/uu898/getCurrentList")
    @Scheduled(cron = "0/30 * * * * ?")
    public CommonResult<List<GoldPriceUnit>> getUU898Current() throws Exception {
        GoldWatcher goldWatcher = applicationContext.getBean(UU898GoldWatcher.class);
        List<GoldPriceUnit> currentList = goldWatcher.getCurrentList();
        return CommonResult.ok(currentList);
    }

    @ApiOperation("获取dd373当前的金价")
    @GetMapping("/dd373/getCurrentList")
    @Scheduled(cron = "0/30 * * * * ?")
    public CommonResult<List<GoldPriceUnit>> getDD373Current() throws Exception {
        GoldWatcher goldWatcher = applicationContext.getBean(DD373GoldWatcher.class);
        List<GoldPriceUnit> currentList = goldWatcher.getCurrentList();
        return CommonResult.ok(currentList);
    }

    @ApiOperation("获取dd373当前的金价")
    @GetMapping("/dd373/getBaiduCurrent")
    @Scheduled(cron = "0/30 * * * * ?")
    public CommonResult<List<GoldPriceUnit>> getBaiduCurrent() throws Exception {
        GoldWatcher goldWatcher = applicationContext.getBean(BaiduTBWatcher.class);
        List<GoldPriceUnit> currentList = goldWatcher.getCurrentList();
        return CommonResult.ok(currentList);
    }

//    public void cronTest(){
//        System.out.println("haha");
//    }
}
