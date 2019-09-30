package com.wzp.jian3.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Throwables;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.stream.IntStream;

public class AspectUtil {
    private static Logger log = LoggerFactory.getLogger(AspectUtil.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public AspectUtil() {
    }

    public static Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String methodInfo = getMethodInfo(method);
        Long start = System.currentTimeMillis();
        log.info("方法{}开始执行", methodInfo);

        Object e;
        try {
            e = joinPoint.proceed();
        } catch (Throwable var7) {
            StringBuilder params = new StringBuilder(methodInfo);
            if (joinPoint.getArgs() != null) {
                params.append("(").append(paramInfo(joinPoint.getArgs())).append(")");
            }

            log.error("方法{}调用异常,耗时:{},异常信息: {}", new Object[]{params.toString(), System.currentTimeMillis() - start, Throwables.getStackTraceAsString(var7)});
            throw var7;
        }

        log.info("方法{}执行结束,耗时:{}", methodInfo, System.currentTimeMillis() - start);
        return e;
    }

    private static String paramInfo(Object[] args) {
        StringBuilder buf = new StringBuilder();
        if (args != null) {
            IntStream.range(0, args.length).forEach((index) -> {
                if (!(args[index] instanceof InputStream)) {
                    String sParam = "";
                    if (args[index] != null) {
                        sParam = toJson(args[index]);
                    }

                    if (index != 0) {
                        buf.append(", ");
                    }

                    buf.append(sParam);
                }

            });
        }

        return buf.toString();
    }

    private static String getMethodInfo(Method method) {
        StringBuilder buf = (new StringBuilder(method.getDeclaringClass().getSimpleName())).append(".").append(method.getName());

        try {
            StringBuilder e = new StringBuilder();
            ApiOperation api = (ApiOperation) method.getAnnotation(ApiOperation.class);
            if (api != null) {
                e.append("[").append(api.value()).append("]");
            }

            if (e.length() > 0) {
                buf.insert(0, e).append(" : ");
            }
        } catch (Throwable var4) {
            log.error("获取[{}]额外日志信息异常:{}", method, var4.getMessage());
        }

        return buf.toString();
    }

    private static String toJson(Object obj) {
        if (obj == null) {
            return "null";
        } else {
            try {
                return mapper.writeValueAsString(obj);
            } catch (Throwable var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    static {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.registerModule(new SimpleModule());
    }
}
