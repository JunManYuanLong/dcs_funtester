package com.funtester.master.common.bean.manager

import com.funtester.base.bean.AbstractBean
import io.swagger.annotations.ApiModel

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@ApiModel(value = "更新运行进度bean")
class RunInfoBean extends AbstractBean {

    private static final long serialVersionUID = 324085090345038940L;

    @NotBlank
    String host

    @NotEmpty
    String runinfo

    @NotEmpty
    String desc

}
