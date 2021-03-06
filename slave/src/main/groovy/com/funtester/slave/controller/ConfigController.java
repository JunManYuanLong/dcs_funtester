package com.funtester.slave.controller;

import com.funtester.base.bean.Result;
import com.funtester.base.constaint.ThreadBase;
import com.funtester.config.Constant;
import com.funtester.slave.common.basedata.DcsConstant;
import com.funtester.slave.manager.SlaveManager;
import com.funtester.utils.Regex;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "slave测试接口")
@RestController
@RequestMapping(value = "/test")
public class ConfigController {

    private static Logger logger = LogManager.getLogger(ConfigController.class);


    @ApiOperation(value = "更新master节点host")
    @PostMapping(value = "/m")
    public Result findUsers(@RequestBody Map<String, Object> param) {
        String host = param.get("host").toString();
        if (!Regex.isMatch(host, "http://" + Constant.HOST_REGEX)) Result.fail();
        DcsConstant.MASTER_HOST = host;
        return Result.success();
    }

    @ApiOperation(value = "获取远程host")
    @GetMapping(value = "/mh")
    public Result getLocalHost() {
        return Result.success(DcsConstant.MASTER_HOST);
    }

    @ApiOperation(value = "获取状态,提供master验证使用")
    @GetMapping(value = "/s")
    public Result nodeStatus() {
        return Result.success(ThreadBase.needAbort());
    }

    @PostMapping(value = "/stop")
    public Result stop() {
        ThreadBase.stop();
        return Result.success();
    }

    @ApiOperation(value = "刷新节点IP")
    @GetMapping(value = "/ip")
    public Result refreshHost() {
        SlaveManager.getIP();
        return Result.success();
    }

    @ApiOperation(value = "刷新节点IP")
    @GetMapping(value = "/register")
    public Result register() {
        return Result.success(SlaveManager.register());
    }

    @ApiOperation(value = "节点状态,是否存活")
    @GetMapping(value = "/alive")
    public Result alive() {
        return Result.success();
    }

}
