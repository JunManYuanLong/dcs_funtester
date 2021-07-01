package com.funtester.master.controller

import com.funtester.base.bean.PerformanceResultBean
import com.funtester.base.bean.Result
import com.funtester.master.common.basedata.NodeData
import com.funtester.master.common.bean.manager.RegisterBean
import com.funtester.master.common.bean.manager.RunInfoBean
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@Api(tags = "管理类接口")
@RestController
@RequestMapping(value = "/m")
class Manager {

    private static final Logger logger = LogManager.getLogger(Manager.class);

    @ApiOperation(value = "状态")
    @GetMapping(value = "/status")
    public Result status() {
        def nodes = []
        NodeData.status.each {
            nodes << it
        }
        Result.success(nodes)
    }

    @ApiOperation(value = "可用的节点,对外")
    @GetMapping(value = "/alive")
    public Result alive() {
        Result.success(NodeData.available().size())
    }

    @ApiOperation(value = "注册接口")
    @PostMapping(value = "/register")
    public Result register(@Valid @RequestBody RegisterBean bean) {
        NodeData.register(bean.getUrl(), false)
        Result.success()
    }

    @ApiOperation(value = "更新状态接口")
    @ApiImplicitParam(name = "bean", value = "注册接口请求对象", dataTypeClass = RegisterBean.class)
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody RegisterBean bean) {
        NodeData.register(bean.getUrl(), bean.getStatus())
        Result.success()
    }

    @ApiOperation(value = "更新运行状态")
    @ApiImplicitParam(name = "bean", value = "注册接口请求对象", dataTypeClass = RegisterBean.class)
    @PostMapping(value = "/upinfo")
    public Result updateProgress(@Valid @RequestBody RunInfoBean bean) {
        NodeData.addRunInfo(bean)
        Result.success()
    }

    @ApiOperation(value = "更新测试结果")
    @ApiImplicitParam(name = "bean", value = "测试结果对象", dataTypeClass = PerformanceResultBean.class)
    @PostMapping(value = "/upresult/{mark}")
    public Result updateResult(@PathVariable(value = "mark") int mark, @RequestBody PerformanceResultBean bean) {

    }


}
