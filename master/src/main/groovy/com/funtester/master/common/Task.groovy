package com.funtester.master.common


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
        logger.info("定时任务执行完毕! 时间:{}", Time.getDate())
    }


}
