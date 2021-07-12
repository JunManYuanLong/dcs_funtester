package com.funtester.master.manaer

import com.funtester.master.common.config.SlaveApi
import com.funtester.slave.common.bean.run.GroovyScript
import com.funtester.slave.common.bean.run.HttpRequest
import com.funtester.slave.common.bean.run.HttpRequests
import com.funtester.slave.common.bean.run.LocalMethod
import com.funtester.slave.common.bean.run.ManyRequest

/**
 * master节点HTTP接口功能管理类
 */
class MasterManager extends MasterHttp {

/**
 * 运行单请求用例
 * @param host
 * @param request
 * @return
 */
    static boolean runRequest(String host, HttpRequest request) {
        String url = SlaveApi.RUN_REQUEST;
        def response = getPostResponse(host, url, request)
        isRight(response)
    }

/**
 * 运行多请求用例
 * @param host
 * @param requests
 * @return
 */
    static boolean runRequests(String host, HttpRequests requests) {
        String url = SlaveApi.RUN_REQUESTS;
        def response = getPostResponse(host, url, requests)
        isRight(response)
    }

/**
 * 运行本地方法用例
 * @param host
 * @param method
 * @return
 */
    static boolean runMethod(String host, LocalMethod method) {
        String url = SlaveApi.RUN_METHOD;
        def response = getPostResponse(host, url, method)
        isRight(response)
    }

/**
 * 运行Groovy脚本,或者Java脚本
 * @param host
 * @param script
 * @return
 */
    static boolean runScript(String host, GroovyScript script) {
        String url = SlaveApi.RUN_SCRIPT;
        def response = getPostResponse(host, url, script)
        isRight(response)
    }

/**
 * 运行放大器
 * @param host
 * @param request
 * @return
 */
    static boolean runMany(String host, ManyRequest request) {
        String url = SlaveApi.RUN_MANY;
        def response = getPostResponse(host, url, request)
        isRight(response)
    }

/**
 * 获取节点运行状态,以备第二种节点使用
 * @param host
 * @return
 */
    static boolean progress(String host) {
        String url = SlaveApi.PROGRESS;
        def response = getGetResponse(host, url)
        isRight(response)
    }

/**
 * 结束节点运行
 * @param host
 * @return
 */
    static boolean stop(String host) {
        String url = SlaveApi.STOP;
        def response = getPostResponse(host, url)
        isRight(response)
    }

/**
 * 获取节点运行状态
 * @param host
 * @return
 */
    static boolean status(String host) {
        String url = SlaveApi.STATUS;
        def response = getGetResponse(host, url, null)
        isRight(response) && response.getBoolean("data")
    }

/**
 * 节点是否存活
 * @param host
 * @return
 */
    static boolean alive(String host) {
        String url = SlaveApi.ALIVE;
        def response = getGetResponse(host, url, null)
        isRight(response)
    }
}
