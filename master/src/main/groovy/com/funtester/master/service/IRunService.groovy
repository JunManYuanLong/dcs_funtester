package com.funtester.master.service

import com.funtester.slave.common.bean.run.GroovyScript
import com.funtester.slave.common.bean.run.HttpRequest
import com.funtester.slave.common.bean.run.HttpRequests
import com.funtester.slave.common.bean.run.LocalMethod

interface IRunService {

    public int runRequest(HttpRequest request)

    public int runRequests(HttpRequests request)

    public int runMethod(LocalMethod method)

    public int runScript(GroovyScript script)

}