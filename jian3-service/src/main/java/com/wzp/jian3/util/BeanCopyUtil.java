package com.wzp.jian3.util;

import com.github.pagehelper.Page;
import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeanCopyUtil {
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    public BeanCopyUtil() {
    }

    public static <T> T objConvert(Object obj, Class<T> toObj) {
        return null == obj ? null : dozer.map(obj, toObj);
    }

    public static void copy(Object source, Object toObj) {
        if (null != source) {
            dozer.map(source, toObj);
        }
    }

    public static <T> List<T> convertList(Collection<?> collection, Class<T> toObj) {
        if (collection == null) {
            return null;
        } else if (toObj == null) {
            return null;
        } else {
            List<T> result = new ArrayList<>();
            for (Object o : collection) {
                result.add(dozer.map(o, toObj));
            }

            return result;
        }
    }

    public static <T> List<T> convertPage(Page page, Class<T> toObj) {
        if (page == null) {
            return null;
        } else if (toObj == null) {
            return null;
        } else {
            Page<T> newPage = new Page<>();
            newPage.setPageNum(page.getPageNum());
            newPage.setPageSize(page.getPageSize());
            newPage.setOrderBy(page.getOrderBy());
            newPage.setPages(page.getPages());
            newPage.setTotal(page.getTotal());

            for (Object o : page) {
                newPage.add(dozer.map(o, toObj));
            }

            return newPage;
        }
    }
}

