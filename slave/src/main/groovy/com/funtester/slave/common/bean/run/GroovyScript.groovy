package com.funtester.slave.common.bean.run

import com.funtester.base.bean.AbstractBean
import io.swagger.annotations.ApiModel

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@ApiModel(value = "Groovy脚本性能测试参数")
class GroovyScript extends AbstractBean implements Serializable{

    private static final long serialVersionUID = 968033335615941L;

    @NotNull
    Integer mark

    @NotEmpty
    String script

    List<String> params

}
