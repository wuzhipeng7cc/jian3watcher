package com.wzp.jian3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.wzp.jian3")
@EnableFeignClients(value = "com.wzp.jian3")
@EnableTransactionManagement
@EnableScheduling
@MapperScan("com.wzp.jian3.db.mapper")
public class Main {


        public static void main(String[] args) {
            SpringApplication.run(Main.class, args);
        }

}
