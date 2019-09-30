package com.wzp.jian3.controller.watcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: wzp
 * date: 2019/9/29
 */
@RestController
@RefreshScope
public class TestController {

    @Value("${test.text}")
    private String text;
    @RequestMapping("/hello")
    public void hello(){
        System.out.println(text);
        System.out.println("2");
    }
}
