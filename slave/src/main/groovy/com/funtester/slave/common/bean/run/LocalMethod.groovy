package com.funtester.slave.common.bean.run


import com.funtester.base.bean.AbstractBean
import io.swagger.annotations.ApiModel

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@ApiModel(value = "本地方法性能测试参数")
class LocalMethod extends AbstractBean implements Serializable{

    private static final long serialVersionUID = 35205992379274029l;

    @NotNull
    Integer mark

    @NotEmpty
    @Pattern(regexp = "com\\..*main", message = "方法名错误")
    String methodName

    List<String> params

}
