package com.funtester.slave.manager

import com.alibaba.fastjson.JSONObject
import com.funtester.base.bean.AbstractBean
import com.funtester.httpclient.FunLibrary
import com.funtester.slave.common.basedata.DcsConstant
import org.apache.commons.lang3.StringUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class SlaveHttp extends FunLibrary {

    private static final Logger logger = LogManager.getLogger(SlaveHttp.class);

    static JSONObject getGetResponse(String url, JSONObject args) {
        if (StringUtils.isAnyBlank(DcsConstant.MASTER_HOST, DcsConstant.LOCAL_HOST)) return null
        def get = getHttpGet(DcsConstant.MASTER_HOST + url, args)
        get.addHeader(DcsConstant.FUNTESTER)
        getHttpResponse(get)
    }

    static JSONObject getGetResponse(String url) {
        getGetResponse(url, null)
    }

    static JSONObject getPostResponse(String url, JSONObject params) {
        if (StringUtils.isAnyBlank(DcsConstant.MASTER_HOST, DcsConstant.LOCAL_HOST)) return null
        def post = getHttpPost(DcsConstant.MASTER_HOST + url, params.toString())
        post.addHeader(DcsConstant.FUNTESTER)
        getHttpResponse(post)
    }

    static JSONObject getPostResponse(String url, AbstractBean bean) {
        if (StringUtils.isAnyBlank(DcsConstant.MASTER_HOST, DcsConstant.LOCAL_HOST)) return null
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
