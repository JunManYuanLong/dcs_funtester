package com.funtester.slave.common.basedata;

import com.funtester.config.PropertyUtils;

/**
 * 常量配置类
 */
public class DcsConstant {

    private static PropertyUtils.Property property = PropertyUtils.getProperties("fun");

    public static final String HEADER_KEY = "funtester";

    public static final String HEADER_VALUE = property.getProperty("key");

    public static String MASTER_HOST = property.getProperty("master.host");

}
