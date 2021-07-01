package com.funtester.master.common.bean.manager

import com.funtester.base.bean.AbstractBean

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class RunInfoBean extends AbstractBean {

    private static final long serialVersionUID = 324085090345038940L;

    @NotEmpty
    String runinfo

    @NotEmpty
    String task

    @NotNull
    Integer mark

}
