package com.funtester.slave.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.funtester.base.bean.PerformanceResultBean;
import com.funtester.base.bean.Result;
import com.funtester.base.constaint.ThreadBase;
import com.funtester.config.Constant;
import com.funtester.frame.SourceCode;
import com.funtester.frame.execute.Concurrent;
import com.funtester.frame.thread.RequestThreadTimes;
import com.funtester.httpclient.FunRequest;
import com.funtester.slave.common.FunData;
import com.funtester.slave.common.bean.HttpRequest;
import com.funtester.slave.common.bean.HttpRequest2;
import com.funtester.slave.template.ListRequestMode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "DCS_FunTester性能测试服务HTTP接口")
@RestController
@RequestMapping(value = "/test")
public class TestController {

    private static Logger logger = LogManager.getLogger(TestController.class);


    @GetMapping(value = "/get")
    public Result findUsers() {
        return Result.success();
    }

    /**
     * 获取测试结果
     *
     * @param id 测试标记id
     * @return
     */
    @ApiOperation(value = "获取测试结果")
    @GetMapping(value = "/get/{id}")
    public Result getRunResult(@PathVariable(name = "id") int id) {
        PerformanceResultBean performanceResultBean = FunData.results.get(id);
        return Result.success(performanceResultBean);
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

    @PostMapping(value = "/stop")
    public Result stop() {
        ThreadBase.stop();
        return Result.success();
    }

    @ApiOperation(value = "测试GET接口")
    @GetMapping(value = "/test")
    public Result test() {
        return Result.success();
    }

    @ApiOperation(value = "单个请求性能测试")
    @ApiImplicitParam(name = "reqsut", value = "单请求参数", dataTypeClass = HttpRequest.class)
    @PostMapping(value = "/post")
    public Result tests(@Valid @RequestBody HttpRequest request) {
        if (!ThreadBase.needAbort()) return Result.fail();
        JSONObject r = request.getRequest();
        HttpRequestBase re = FunRequest.initFromJson(r).getRequest();
        Integer times = request.getTimes();
        String mode = request.getMode();
        Integer thread = request.getThread();
        Integer runup = request.getRunup();
        String desc = request.getDesc();
        if (mode.equalsIgnoreCase("ftt")) {
            Constant.RUNUP_TIME = runup;
            RequestThreadTimes task = new RequestThreadTimes(re, times);
            Concurrent concurrent = new Concurrent(task, thread, desc);
            return Result.success(execute(concurrent));
        }
        return Result.fail();
    }

    /**
     * 异步执行的用例的方法
     *
     * @param concurrent
     * @return
     */
    public int execute(Concurrent concurrent) {
        int mark = SourceCode.getMark();
        new Thread(new Runnable() {
            @Override
            public void run() {
                PerformanceResultBean start = concurrent.start();
                FunData.results.put(mark, start);
            }
        }).start();
        return mark;
    }

    @ApiOperation(value = "请求队列性能测试")
//    @ApiImplicitParams({
//    })
    @ApiImplicitParam(name = "reqsuts", value = "请求队列", dataTypeClass = HttpRequest2.class)
    @PostMapping(value = "/post2")
    public Result tests2(@Valid @RequestBody HttpRequest2 request) {
        if (!ThreadBase.needAbort()) return Result.fail();
        JSONArray requests = request.getRequests();
        request.print();
        List<HttpRequestBase> res = new ArrayList<>();
        requests.forEach(f -> {
            res.add(FunRequest.initFromString(JSON.toJSONString(f)).getRequest());
        });
        Integer times = request.getTimes();
        String mode = request.getMode();
        Integer thread = request.getThread();
        Integer runup = request.getRunup();
        String desc = request.getDesc();
        if (mode.equalsIgnoreCase("ftt")) {
            Constant.RUNUP_TIME = runup;
            ListRequestMode task = new ListRequestMode(res, times);
            Concurrent concurrent = new Concurrent(task, thread, desc);
            return Result.success(execute(concurrent));
        }
        return Result.success();
    }

    @ApiOperation(value = "测试POST接口")
    @PostMapping(value = "/p")
    public Result tes33ts() {
        return Result.success();
    }

}
