package com.funtester.slave.manager

import com.alibaba.fastjson.JSONObject
import com.funtester.base.bean.PerformanceResultBean
import com.funtester.base.constaint.ThreadBase
import com.funtester.slave.common.basedata.DcsConstant
import com.funtester.slave.common.config.MasterApi
import com.funtester.slave.common.config.ServerConfig
import com.funtester.slave.util.DcsHttp
import org.apache.commons.lang3.StringUtils

class DcsManager extends DcsHttp {


    static void getIP() {
        if (StringUtils.isBlank(DcsConstant.MASTER_HOST)) return
        String url = MasterApi.GET_IP
        def response = getHttpResponse(getHttpGet(DcsConstant.MASTER_HOST + url))
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
        String url = MasterApi.UPDATE
        def params = new JSONObject()
        params.url = DcsConstant.LOCAL_HOST
        params.status = ThreadBase.needAbort()
        def response = getPostResponse(url, params)
        isRight(response)
    }

    static boolean updateProgress() {
        String url = MasterApi.UPDATE_INFO
        def params = new JSONObject()
        params.runinfo = ThreadBase.needAbort() ? "空闲状态" : ThreadBase.progress.runInfo
        params.desc = ThreadBase.needAbort() ? DEFAULT_STRING : ThreadBase.progress.taskDesc
        params.host = DcsConstant.LOCAL_HOST
        def response = getPostResponse(url, params)
        isRight(response)
    }

    static boolean updateResult(PerformanceResultBean bean, int mark) {
        String url = MasterApi.UPDATE_RESULT + mark;
        def response = getPostResponse(url, bean)
        isRight(response)
    }

}
