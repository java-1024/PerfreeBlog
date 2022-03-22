package com.perfree;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.perfree.config.UniqueNameGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { DruidDataSourceAutoConfigure.class})
@MapperScan(value = "com.perfree.mapper", nameGenerator = UniqueNameGenerator.class)
public class Application{

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
