package com.funtester.slave.service.impl;

import com.funtester.slave.common.bean.run.*;

public interface IRunService {

    public void runRequest(HttpRequest request);

    public void runMany(ManyRequest request);

    public void runRequests(HttpRequests request);

    public void runMethod(LocalMethod method);

    public void runScript(GroovyScript script);

}
