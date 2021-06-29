package com.funtester.master.controller


import com.funtester.base.bean.Result
import com.funtester.master.common.basedata.NodeData
import io.swagger.annotations.Api
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@Api(tags = "管理类接口")
@RestController
@RequestMapping(value = "/m")
class Manager {

    private static final Logger logger = LogManager.getLogger(Manager.class);

    @GetMapping(value = "/staus")
    public Result status() {
        def nodes = []
        NodeData.status.each {
            if (it.value) nodes << it
        }
        Result.success(nodes)
    }

    @PostMapping(value = "/register")
    public Result register(@Valid @RequestBody com.funtester.master.common.bean.manager.RegisterBean bean) {
        NodeData.register(bean.getUrl(), true)
        Result.success()
    }

    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody com.funtester.master.common.bean.manager.RegisterBean bean) {
        NodeData.register(bean.getUrl(), bean.getStatus())
        Result.success()
    }


}
