package com.funtester.master.common.bean.manager

import com.funtester.base.bean.AbstractBean
import io.swagger.annotations.ApiModel

import javax.validation.constraints.Pattern

@ApiModel(value = "slave节点注册bean")
class RegisterBean extends AbstractBean {

    private static final long serialVersionUID = 3240850903838940L;

    @Pattern(regexp = "http://((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))):([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{4}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])", message = "地址错误")
    String url

    Boolean status

}
