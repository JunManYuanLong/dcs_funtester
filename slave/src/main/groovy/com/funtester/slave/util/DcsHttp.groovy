package com.funtester.slave.util

import com.alibaba.fastjson.JSONObject
import com.funtester.httpclient.FunLibrary
import com.funtester.slave.common.basedata.DcsConstant

class DcsHttp extends FunLibrary {

    static JSONObject getGetResponse(String url, JSONObject args) {
        def get = getHttpGet(url, args)
        get.addHeader(DcsConstant.FUNTESTER)
        getHttpResponse(get)
    }

    static JSONObject getPostResponse(String url, JSONObject params) {
        def post = getHttpPost(url, params.toString())
        post.addHeader(DcsConstant.FUNTESTER)
        getHttpResponse(post)
    }

    static boolean isRight(JSONObject response) {
        try {
            return response.getInteger("data") == 0
        } catch (e) {
            return false
        }
    }

}
