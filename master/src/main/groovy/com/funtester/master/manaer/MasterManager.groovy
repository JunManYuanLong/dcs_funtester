package com.funtester.master.manaer

import com.funtester.master.common.config.SlaveApi
import com.funtester.slave.common.bean.run.GroovyScript
import com.funtester.slave.common.bean.run.HttpRequest
import com.funtester.slave.common.bean.run.HttpRequests
import com.funtester.slave.common.bean.run.LocalMethod

class MasterManager extends MasterHttp {

    static boolean runRequest(String host, HttpRequest request) {
        String url = SlaveApi.RUN_REQUEST;
        def response = getPostResponse(host, url, request)
        isRight(response)
    }

    static boolean runRequests(String host, HttpRequests requests) {
        String url = SlaveApi.RUN_REQUESTS;
        def response = getPostResponse(host, url, requests)
        isRight(response)
    }

    static boolean runMethod(String host, LocalMethod method) {
        String url = SlaveApi.RUN_METHOD;
        def response = getPostResponse(host, url, method)
        isRight(response)
    }

    static boolean runScript(String host, GroovyScript script) {
        String url = SlaveApi.RUN_SCRIPT;
        def response = getPostResponse(host, url, script)
        isRight(response)
    }

    static boolean progress(String host) {
        String url = SlaveApi.PROGRESS;
        def response = getGetResponse(host, url)
        isRight(response)
    }

    static boolean stop(String host) {
        String url = SlaveApi.STOP;
        def response = getGetResponse(host, url)
        isRight(response)
    }

    static boolean status(String host) {
        String url = SlaveApi.STATUS;
        def response = getGetResponse(host, url, null)
        isRight(response) && response.getBoolean("data")
    }

}