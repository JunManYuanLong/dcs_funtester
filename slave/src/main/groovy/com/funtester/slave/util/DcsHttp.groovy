package com.funtester.slave.util

import com.alibaba.fastjson.JSONObject
import com.funtester.base.bean.AbstractBean
import com.funtester.httpclient.FunLibrary
import com.funtester.slave.common.basedata.DcsConstant
import org.apache.commons.lang3.StringUtils

class DcsHttp extends FunLibrary {

    static JSONObject getGetResponse(String url, JSONObject args) {
        def get = getHttpGet(DcsConstant.MASTER_HOST + url, args)
        get.addHeader(DcsConstant.FUNTESTER)
        if (StringUtils.isAnyBlank(DcsConstant.MASTER_HOST, DcsConstant.LOCAL_HOST)) return null
        getHttpResponse(get)
    }

    static JSONObject getPostResponse(String url, JSONObject params) {
        def post = getHttpPost(DcsConstant.MASTER_HOST + url, params.toString())
        post.addHeader(DcsConstant.FUNTESTER)
        if (StringUtils.isAnyBlank(DcsConstant.MASTER_HOST, DcsConstant.LOCAL_HOST)) return null
        getHttpResponse(post)
    }

    static JSONObject getPostResponse(String url, AbstractBean bean) {
        def post = getHttpPost(DcsConstant.MASTER_HOST + url, bean.toString())
        post.addHeader(DcsConstant.FUNTESTER)
        if (StringUtils.isAnyBlank(DcsConstant.MASTER_HOST, DcsConstant.LOCAL_HOST)) return null
        getHttpResponse(post)
    }

    static boolean isRight(JSONObject response) {
        try {
            return response.getIntValue("code") == 0
        } catch (e) {
            return false
        }
    }

}
