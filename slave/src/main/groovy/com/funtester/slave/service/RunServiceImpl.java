package com.funtester.slave.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.funtester.base.bean.PerformanceResultBean;
import com.funtester.config.Constant;
import com.funtester.frame.execute.Concurrent;
import com.funtester.frame.thread.RequestThreadTimes;
import com.funtester.httpclient.FunRequest;
import com.funtester.slave.common.bean.HttpRequest;
import com.funtester.slave.common.bean.HttpRequest2;
import com.funtester.slave.manager.DcsManager;
import com.funtester.slave.service.impl.IRunService;
import com.funtester.slave.template.ListRequestMode;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RunServiceImpl implements IRunService {

    @Async
    @Override
    public void runRequest(HttpRequest request) {
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
            PerformanceResultBean resultBean = concurrent.start();
            DcsManager.updateResult(resultBean);
        }
    }

    @Async
    @Override
    public void runRequests(HttpRequest2 request) {
        JSONArray requests = request.getRequests();
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
            PerformanceResultBean resultBean = concurrent.start();
            DcsManager.updateResult(resultBean);
        }
    }

}
