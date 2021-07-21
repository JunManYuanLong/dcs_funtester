package com.funtester.slave.common.bean.run

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.funtester.base.bean.AbstractBean
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@ApiModel(value = "基础HTTP请求对象")
class BaseRequest extends AbstractBean {

    private static final long serialVersionUID = -490905323920398509L;

    @Pattern(regexp = "get|post|Get|Post|GET|POST", message = "请求方法错误!")
    String requestType

    @ApiModelProperty(value = "请求的URL")
    @NotBlank
    @Pattern(regexp = "http.*", message = "请求url错误")
    String uri

    @ApiModelProperty(value = "请求GET参数")
    @NotNull
    JSONObject args

    @ApiModelProperty(value = "POST和PUT请求表单参数")
    @NotNull
    JSONObject params

    @ApiModelProperty(value = "POST和PUT请求JSON参数")
    @NotNull
    JSONObject json

    @ApiModelProperty(value = "请求的header")
    @NotNull
    JSONArray headers

}
