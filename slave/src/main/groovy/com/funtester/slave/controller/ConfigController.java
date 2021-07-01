package com.funtester.slave.controller;

import com.funtester.base.bean.Result;
import com.funtester.base.constaint.ThreadBase;
import com.funtester.config.Constant;
import com.funtester.slave.common.basedata.DcsConstant;
import com.funtester.utils.Regex;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@Api(tags = "slave测试接口")
@RestController
@RequestMapping(value = "/test")
public class ConfigController {

    private static Logger logger = LogManager.getLogger(ConfigController.class);


    @ApiOperation(value = "更新master节点host")
    @PostMapping(value = "/m/{host}")
    public Result findUsers(@PathVariable(value = "host") String host) {
        if (!Regex.isMatch(host, "http://" + Constant.HOST_REGEX)) Result.fail();
        DcsConstant.MASTER_HOST = host;
        return Result.success();
    }

    @ApiOperation(value = "获取远程host")
    @GetMapping(value = "/mh")
    public Result getLocalHost() {
        return Result.success(DcsConstant.MASTER_HOST);
    }

    @PostMapping(value = "/stop")
    public Result stop() {
        ThreadBase.stop();
        return Result.success();
    }

}
