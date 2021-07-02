package com.funtester.slave.service.impl;

import com.funtester.slave.common.bean.run.GroovyScript;
import com.funtester.slave.common.bean.run.HttpRequest;
import com.funtester.slave.common.bean.run.HttpRequests;
import com.funtester.slave.common.bean.run.LocalMethod;

public interface IRunService {

    public void runRequest(HttpRequest request);

    public void runRequests(HttpRequests request);

    public void runMethod(LocalMethod method);

    public void runScript(GroovyScript script);

}
