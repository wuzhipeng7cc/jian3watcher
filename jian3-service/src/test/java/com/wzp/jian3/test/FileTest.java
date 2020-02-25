package com.wzp.jian3.test;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author wuzhipeng
 * @date 2019/10/1610:09 下午
 */
public class FileTest {
    @Test
    public void testDelete() throws Exception{
        FileOutputStream fos = null;
        File file = new File("/Users/wuzhipeng/wzp_app/game/jian3/test1.txt");
        if (!file.exists()) {
            file.createNewFile();
            // 构造写入文件内容
            fos = new FileOutputStream(file);
            fos.write("Hello Wolrd".getBytes());
        }
        InputStream inputStream = new FileInputStream(file);
        boolean delete = file.delete();
        System.out.println(delete);
    }

}
