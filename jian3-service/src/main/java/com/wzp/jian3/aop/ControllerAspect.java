package com.wzp.jian3.aop;

import com.wzp.jian3.watcher.gold.AbstractGoldWatcher;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.selector.Html;

@Component
@Aspect
@Slf4j
public class ControllerAspect {
    @Around("execution(public * com.wzp.jian3.controller.*.*Controller.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object o = AspectUtil.logAround(joinPoint);
        return o;
    }
}