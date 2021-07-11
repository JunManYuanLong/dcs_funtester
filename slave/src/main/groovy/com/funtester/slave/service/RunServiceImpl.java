package com.funtester.slave.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.funtester.base.bean.PerformanceResultBean;
import com.funtester.base.exception.FailException;
import com.funtester.config.Constant;
import com.funtester.frame.execute.Concurrent;
import com.funtester.frame.execute.ExecuteGroovy;
import com.funtester.frame.execute.ExecuteSource;
import com.funtester.frame.thread.RequestThreadTimes;
import com.funtester.httpclient.FunLibrary;
import com.funtester.httpclient.FunRequest;
import com.funtester.slave.common.bean.run.*;
import com.funtester.slave.manager.SlaveManager;
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
            SlaveManager.updateResult(resultBean, request.getMark());
        }
    }

    @Async
    @Override
    public void runMany(ManyRequest request) {
        FunRequest funRequest = FunRequest.initFromJson(request.toJson());
        HttpRequestBase requestBase = funRequest.getRequest();
        Integer times = request.getTimes();
        try {
            for (int i = 0; i < times; i++) {
                FunLibrary.executeSimlple(requestBase);
            }
        } catch (Exception e) {
            FailException.fail(e.getMessage());
        }
    }

    @Async
    @Override
    public void runRequests(HttpRequests request) {
        List<BaseRequest> requests = request.getRequests();
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
            SlaveManager.updateResult(resultBean, request.getMark());
        }
    }

    @Async
    @Override
    public void runMethod(LocalMethod method) {
        String methodName = method.getMethodName();
        List<String> params = method.getParams();
        ExecuteSource.executeMethod(methodName, params.toArray());
    }

    @Override
    public void runScript(GroovyScript script) {
        //此处留意,以后支持传参
        ExecuteGroovy.executeScript(script.getScript());
    }


}
