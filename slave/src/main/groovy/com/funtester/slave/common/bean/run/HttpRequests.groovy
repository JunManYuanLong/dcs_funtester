package com.funtester.slave.common.bean.run

import com.alibaba.fastjson.JSONArray
import com.funtester.base.bean.AbstractBean
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.Range

import javax.validation.constraints.NotNull

@ApiModel(value = "多请求测试参数")
class HttpRequests extends AbstractBean implements Serializable {

    private static final long serialVersionUID = -324324327948379L;

    @NotNull
    Integer mark

    @ApiModelProperty(value = "http请求队列")
    @NotNull
    JSONArray requests

    @ApiModelProperty(value = "单线程请求次数")
    @Range(min = 1L, max = 100L)
    Integer times

    @ApiModelProperty(value = "模式,内测阶段只支持ftt")
    String mode;

    @ApiModelProperty(value = "线程数")
    @Range(min = 1L, max = 10L)
    Integer thread

    @ApiModelProperty(value = "软启动时间")
    Integer runup

    @ApiModelProperty(value = "用例描述,1-100字符")
    @Length(min = 1, max = 100)
    String desc

}
