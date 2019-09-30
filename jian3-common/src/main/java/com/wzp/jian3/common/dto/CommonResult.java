package com.wzp.jian3.common.dto;

import lombok.Data;

/**
 * @author wuzhipeng
 * @date 2019-09-3015:18
 */
@Data
public class CommonResult<T> {
    private int status;

    private T data;


    private String msg;

    public static <T> CommonResult ok(T data) {
        CommonResult commonResult = new CommonResult<>();
        commonResult.setStatus(200);
        commonResult.setData(data);
        return commonResult;
    }

}
