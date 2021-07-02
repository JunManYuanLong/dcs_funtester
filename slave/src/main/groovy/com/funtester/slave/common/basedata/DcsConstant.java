package com.funtester.slave.common.basedata;

import com.funtester.config.PropertyUtils;
import com.funtester.httpclient.FunLibrary;
import org.apache.http.Header;

/**
 * 常量配置类
 */
public class DcsConstant {

    private static PropertyUtils.Property property = PropertyUtils.getProperties("fun");

    public static final String HEADER_KEY = "funtester";

    public static final String HEADER_VALUE = property.getProperty("key");

    public static final Header FUNTESTER = FunLibrary.getHeader(HEADER_KEY, HEADER_VALUE);

    public static String MASTER_HOST = property.getProperty("master.host");

    public static String LOCAL_HOST;

}
