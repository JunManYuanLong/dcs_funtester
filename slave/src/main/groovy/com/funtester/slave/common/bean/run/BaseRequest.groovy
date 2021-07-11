package com.funtester.slave.common.bean.run

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.funtester.base.bean.AbstractBean
import io.swagger.annotations.ApiModel

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@ApiModel(value = "单请求放大器参数")
class BaseRequest extends AbstractBean {

    private static final long serialVersionUID = -49090532920398509L;

    @Pattern(regexp = "get|post|Get|Post|GET|POST", message = "请求方法错误!")
    String requestType

    @NotBlank
    @Pattern(regexp = "http.*", message = "请求url错误")
    String uri

    @NotNull
    JSONObject args

    @NotNull
    JSONObject params

    @NotNull
    JSONObject json

    @NotNull
    JSONArray headers

}
