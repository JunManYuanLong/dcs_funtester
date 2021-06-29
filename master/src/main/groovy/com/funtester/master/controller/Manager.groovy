package com.funtester.master.controller


import com.funtester.base.bean.Result
import com.funtester.master.common.basedata.NodeData
import com.funtester.master.common.bean.manager.RegisterBean
import io.swagger.annotations.Api
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

    @ApiOperation(value = "单个请求性能测试")
    @GetMapping(value = "/staus")
    public Result status() {
        def nodes = []
        NodeData.status.each {
            nodes << it
        }
        Result.success(nodes)
    }

    @GetMapping(value = "/alive")
    public Result alive() {
        Result.success(NodeData.available().size())
    }

    @PostMapping(value = "/register")
    public Result register(@Valid @RequestBody RegisterBean bean) {
        NodeData.register(bean.getUrl(), false)
        Result.success()
    }

    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody RegisterBean bean) {
        NodeData.register(bean.getUrl(), bean.getStatus())
        Result.success()
    }


}
