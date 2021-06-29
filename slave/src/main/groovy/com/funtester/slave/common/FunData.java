package com.funtester.slave.common;

import com.funtester.base.bean.PerformanceResultBean;

import java.util.concurrent.ConcurrentHashMap;

public class FunData {

    public static ConcurrentHashMap<Integer, PerformanceResultBean> results = new ConcurrentHashMap<Integer, PerformanceResultBean>();

}
