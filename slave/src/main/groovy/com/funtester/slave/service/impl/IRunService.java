package com.funtester.slave.service.impl;

import com.funtester.slave.common.bean.HttpRequest;
import com.funtester.slave.common.bean.HttpRequest2;

public interface IRunService {

    public void runRequest(HttpRequest request);

    public void runRequests(HttpRequest2 request);


}
