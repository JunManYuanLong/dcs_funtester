package com.funtester.slave.controller;

import com.funtester.base.bean.Result;
import com.funtester.base.constaint.ThreadBase;
import com.funtester.slave.common.bean.HttpRequest;
import com.funtester.slave.common.bean.HttpRequest2;
import com.funtester.slave.service.impl.IRunService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "slave节点运行模块接口")
@RestController
@RequestMapping(value = "/run")
public class RunController {

    private static Logger logger = LogManager.getLogger(RunController.class);

    IRunService runService;

    @Autowired
    public RunController(IRunService runService) {
        this.runService = runService;
    }

    /**
     * 获取运行状态
     *
     * @return
     */
    @GetMapping(value = "/progress")
    @ApiOperation(value = "获取运行状态")
    public Result progeress() {
        String s = ThreadBase.progress == null ? "没有运行任务" : ThreadBase.progress.runInfo;
        return Result.success(s);
    }

    @ApiOperation(value = "单个请求性能测试")
    @ApiImplicitParam(name = "reqsut", value = "单请求参数", dataTypeClass = HttpRequest.class)
    @PostMapping(value = "/r")
    public Result tests(@Valid @RequestBody HttpRequest request) {
        if (!ThreadBase.needAbort()) return Result.fail();
        runService.runRequest(request);
        return Result.success();
    }

    @ApiOperation(value = "请求队列性能测试")
    @ApiImplicitParam(name = "reqsuts", value = "请求队列", dataTypeClass = HttpRequest2.class)
    @PostMapping(value = "/rs")
    public Result tests2(@Valid @RequestBody HttpRequest2 requests) {
        runService.runRequests(requests);
        return Result.success();
    }

    @ApiOperation(value = "请求自带方法性能测试")
    @ApiImplicitParam(name = "f", value = "请求队列", dataTypeClass = HttpRequest2.class)
    @PostMapping(value = "/post2")
    public Result tests3(@Valid @RequestBody HttpRequest2 requests) {
        return Result.success();
    }


}
