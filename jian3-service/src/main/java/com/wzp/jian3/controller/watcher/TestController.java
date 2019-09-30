package com.wzp.jian3.controller.watcher;

import com.wzp.jian3.db.mapper.TestMapper;
import com.wzp.jian3.db.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author: wzp
 * date: 2019/9/29
 */
@RestController
public class TestController {
    @Autowired
    private TestMapper testMapper;

    @RequestMapping("/hello")
    public void hello(){
        List<Test> tests = testMapper.selectAll();
        System.out.println("2");
    }
}
