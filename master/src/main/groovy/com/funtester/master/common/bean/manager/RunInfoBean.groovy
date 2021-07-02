package com.funtester.master.common.bean.manager

import com.funtester.base.bean.AbstractBean

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

class RunInfoBean extends AbstractBean {

    private static final long serialVersionUID = 324085090345038940L;

    @NotBlank
    String host

    @NotEmpty
    String runinfo

    @NotEmpty
    String desc

}
