package com.funtester.slave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;


//@MapperScan(value = "com.okay.family.mapper")
@ServletComponentScan
@EnableAsync
@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableOpenApi
public class SlaveApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlaveApplication.class, args);
    }


}
