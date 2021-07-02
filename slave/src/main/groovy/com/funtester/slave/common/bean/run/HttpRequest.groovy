package com.funtester.slave.common.bean.run

import com.alibaba.fastjson.JSONObject
import com.funtester.base.bean.AbstractBean
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.Range

import javax.validation.constraints.NotNull

@ApiModel(value = "单请求性能测试参数")
class HttpRequest extends AbstractBean implements Serializable {

    private static final long serialVersionUID = 324324327948379L;

    @NotNull
    Integer mark

    @ApiModelProperty(value = "http请求")
    @NotNull
    JSONObject request

    @ApiModelProperty(value = "单线程请求次数")
    @Range(min = 1L, max = 2000L)
    Integer times

    @ApiModelProperty(value = "模式,内测阶段只支持ftt")
    String mode;
    //线程数,这里默认固定线程模式
    @ApiModelProperty(value = "线程数")
    @Range(min = 1L, max = 100L)
    Integer thread

    //软启动时间
    @ApiModelProperty(value = "软启动时间")
    Integer runup

    //用例描述
    @ApiModelProperty(value = "用例描述,1-100字符")
    @Length(min = 1, max = 100)
    String desc

}
