package com.funtester.master.manaer

import com.alibaba.fastjson.JSONObject
import com.funtester.base.bean.AbstractBean
import com.funtester.httpclient.FunLibrary
import com.funtester.slave.common.basedata.DcsConstant
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class MasterHttp extends FunLibrary {

    private static final Logger logger = LogManager.getLogger(MasterHttp.class);

    static JSONObject getGetResponse(String slave, String api, JSONObject args) {
        def get = getHttpGet(DcsConstant.MASTER_HOST + api, args)
        get.addHeader(DcsConstant.FUNTESTER)
        getHttpResponse(get)
    }

    static JSONObject getGetResponse(String slave, String api) {
        getGetResponse(slave, api)
    }

    static JSONObject getPostResponse(String slave, String api, JSONObject params) {
        def post = getHttpPost(DcsConstant.MASTER_HOST + api, params.toString())
        post.addHeader(DcsConstant.FUNTESTER)
        getHttpResponse(post)
    }

    static JSONObject getPostResponse(String slave, String url, AbstractBean bean) {
        def post = getHttpPost(DcsConstant.MASTER_HOST + url, bean.toString())
        post.addHeader(DcsConstant.FUNTESTER)
        getHttpResponse(post)
    }

    static boolean isRight(JSONObject response) {
        try {
            return response.getIntValue("code") == 0
        } catch (e) {
            logger.warn("响应出错:{}", response.toString())
            return false
        }
    }
}
