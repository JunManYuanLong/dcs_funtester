package com.funtester.slave.common;

import com.funtester.slave.manager.SlaveManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartRun implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(StartRun.class);

    @Override
    public void run(String... args) {
        SlaveManager.getIP();
        SlaveManager.register();
        logger.info("程序初始化运行方法执行完毕……");
    }


}