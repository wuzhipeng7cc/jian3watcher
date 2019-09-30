package com.wzp.jian3.exceptionHandler;

import com.wzp.jian3.common.dto.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author: wzp
 * date: 2019/10/2
 */
@ControllerAdvice
@Slf4j
public class Jian3ControllerAdvice {


    @ExceptionHandler({Exception.class})
    @ResponseBody
    public CommonResult handException(Exception e) throws Exception {
        log.error("抓到异常e:", e);
        CommonResult<Object> result = new CommonResult<>();
        result.setStatus(500);
        result.setMsg(e.getMessage());
        return result;
    }
}
