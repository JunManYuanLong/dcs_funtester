package com.funtester.slave.common

import com.funtester.frame.Output
import com.funtester.frame.SourceCode
import com.funtester.slave.common.config.ServerConfig
import com.funtester.utils.Time
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Task {

    private static Logger logger = LogManager.getLogger(Task.class);

    /**
     * 定时删除存的过期数据
     * @return
     */
    @Scheduled(cron = "0 0 0/3 * * ?")
    def saveRequestBean() {
        def mark = SourceCode.getMark()
        List<Integer> dels = []
        FunData.results.keySet().each {
            if (mark - it > 7200) {
                dels << it
            }
        }
        dels.each {FunData.results.remove(it)}
        logger.info("定时任务执行完毕! 时间:{}", Time.getDate())
    }

    @Scheduled(cron = "0/1 * * * * ?")
    def mark() {
        Output.output(ServerConfig.serverPort)
        logger.info("d! 时间:{}", Time.getDate())
    }

}
