package com.funtester.master.common.basedata;

import com.funtester.base.bean.PerformanceResultBean;
import com.funtester.base.exception.FailException;
import com.funtester.frame.SourceCode;
import com.funtester.master.common.bean.manager.RunInfoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class NodeData {

    public static ConcurrentHashMap<String, Boolean> status = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, String> runInfos = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Integer, List<PerformanceResultBean>> results = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Integer> time = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Integer> tasks = new ConcurrentHashMap<>();

    public static void register(String host, boolean s) {
        synchronized (status) {
            status.put(host, s);
            mark(host);
        }
    }

    public static List<String> available() {
        synchronized (status) {
            List<String> availables = new ArrayList<>();
            status.forEach((k, v) -> {
                if (v) availables.add(k);
            });
            return availables;
        }
    }

    private static void mark(String host) {
        time.put(host, SourceCode.getMark());
    }

    public static void check() {
        int timeStamp = SourceCode.getMark();
        List<String> hkeys = new ArrayList<>();
        synchronized (status) {
            time.forEach((k, v) -> {
                if (timeStamp - v > 12) {
                    hkeys.add(k);
                }
            });
            hkeys.forEach(f -> status.remove(f));
        }
        synchronized (runInfos) {
            hkeys.forEach(f -> runInfos.remove(f));
        }
        synchronized (tasks) {
            hkeys.forEach(f -> tasks.remove(f));
            tasks.forEach((k, v) -> {
                if (timeStamp - v > 60 * 30) tasks.put(k, 0);
            });
        }
        synchronized (results) {
            List<Integer> tkeys = new ArrayList<>();
            results.forEach((k, v) -> {
                if (k - timeStamp > 3_3600) {
                    tkeys.add(k);
                }
            });
            tkeys.forEach(f -> results.remove(f));
        }
    }

    /**
     * 添加运行信息
     *
     * @param bean
     */
    public static void addRunInfo(RunInfoBean bean) {
        synchronized (runInfos) {
            runInfos.put(bean.getHost(), bean.getRuninfo());
        }
    }

    public static List<String> getRunInfo(String desc) {
        synchronized (runInfos) {
            ArrayList<String> infos = new ArrayList<>();
            runInfos.forEach((k, v) -> {
                if (v.contains(desc)) {
                    infos.add(v);
                }
            });
            return infos;
        }
    }

    /**
     * 添加运行信息
     *
     * @param bean
     */
    public static void addResult(int mark, PerformanceResultBean bean) {
        synchronized (results) {
            results.computeIfAbsent(mark, f -> new ArrayList<PerformanceResultBean>());
            results.get(mark).add(bean);
        }
    }

    public static void addTask(String host, Integer mark) {
        synchronized (tasks) {
            if (status.get(host) != null && status.get(host) == false) {
                tasks.put(host, mark);
            }
        }
    }

    public static List<String> getRunHost(int num) {
        synchronized (status) {
            List<String> available = available();
            if (num > available.size())
                FailException.fail("没有足够节点执行任务");
            List<String> nods = available.subList(0, num);
            nods.forEach(f -> status.put(f, false));
            return nods;
        }


    }

}
