package com.funtester.master.controller

import com.funtester.base.bean.Result
import com.funtester.master.common.basedata.NodeData
import com.funtester.master.manaer.MasterManager
import com.funtester.master.service.IRunService
import com.funtester.slave.common.bean.run.ManyRequest
import com.funtester.slave.common.bean.run.GroovyScript
import com.funtester.slave.common.bean.run.HttpRequest
import com.funtester.slave.common.bean.run.HttpRequests
import com.funtester.slave.common.bean.run.LocalMethod
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@Api(tags = "执行用例模块")
@RestController
@RequestMapping(value = "/run")
class RunController {

    private static final Logger logger = LogManager.getLogger(RunController.class);


    IRunService runService

    @Autowired
    RunController(IRunService runService) {
        this.runService = runService
    }

    @ApiOperation(value = "请求放大器")
    @PostMapping(value = "/many")
    Result runMany(@Valid @RequestBody ManyRequest request) {
        Result.success(runService.runRequest(request))
    }

    @ApiOperation(value = "多节点执行单请求")
    @PostMapping(value = "/req")
    Result runR(@Valid @RequestBody HttpRequest request) {
        Result.success(runService.runRequest(request))
    }

    @ApiOperation(value = "多节点执行多请求")
    @PostMapping(value = "/reqs")
    Result runRs(@Valid @RequestBody HttpRequests requests) {
        Result.success(runService.runRequests(requests))
    }

    @ApiOperation(value = "多节点执行测试用例")
    @PostMapping(value = "/method")
    Result runM(@Valid @RequestBody LocalMethod method) {
        Result.success(runService.runMethod(method))
    }

    @ApiOperation(value = "多节点执行多Groovy脚本")
    @PostMapping(value = "/script")
    Result runS(@Valid @RequestBody GroovyScript script) {
        Result.success(runService.runScript(script))
    }

    @ApiOperation(value = "终止某个任务的执行")
    @PostMapping(value = "/stop/{mark}")
    Result stop(@PathVariable(value = "mark", required = true) int mark) {
        NodeData.tasks.each {
            if (it.getValue() == mark) MasterManager.stop(it.key)
        }
    }

    @PostMapping(value = "/stopall")
    Result over() {
        NodeData.status.each { MasterManager.stop(it.getValue()) }
    }

}
