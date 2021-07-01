package com.funtester.slave.manager

import com.alibaba.fastjson.JSONObject
import com.funtester.base.bean.PerformanceResultBean
import com.funtester.base.constaint.ThreadBase
import com.funtester.slave.common.basedata.DcsConstant
import com.funtester.slave.common.config.MasterApi
import com.funtester.slave.common.config.ServerConfig
import com.funtester.slave.util.DcsHttp

class DcsManager extends DcsHttp {


    static void getIP() {
        String url = MasterApi.GET_IP
        def response = getGetResponse(url)
        if (isRight(response)) {
            DcsConstant.LOCAL_HOST = "http://" + response.getString("data") + ":" + ServerConfig.serverPort
        }
    }

    static boolean register() {
        String url = MasterApi.REGISTER
        def params = new JSONObject()
        params.url = DcsConstant.LOCAL_HOST
        def response = getPostResponse(url, params)
        isRight(response)
    }

    static boolean update() {
        String url = MasterApi.REGISTER
        def params = new JSONObject()
        params.url = DcsConstant.LOCAL_HOST
        params.status = ThreadBase.needAbort()
        def response = getPostResponse(url, params)
        isRight(response)
    }

    static boolean updateProgress() {
        String url = MasterApi.UPDATE_INFO
        def params = new JSONObject()
        params.runinf = ThreadBase.progress.runInfo
        params.desc = ThreadBase.progress.taskDesc
        def response = getPostResponse(url, params.toString())
        isRight(response)
    }

    static boolean updateResult(PerformanceResultBean bean) {
        String url = MasterApi.UPDATE_RESULT + DcsConstant.TASK_MARK;
        def response = getPostResponse(url, bean.toString())
        isRight(response)
    }

}
