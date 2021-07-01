package com.funtester.master.common.basedata;

import com.funtester.master.common.bean.manager.RunInfoBean;
import com.funtester.utils.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class NodeData {

    public static ConcurrentHashMap<String, Boolean> status = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Integer, List<RunInfoBean>> runInfos = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Long> time = new ConcurrentHashMap<>();


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
        time.put(host, Time.getTimeStamp());
    }

    public static void check() {
        long timeStamp = Time.getTimeStamp();
        synchronized (status) {
            List<String> keys = new ArrayList<>();
            time.forEach((k, v) -> {
                if (timeStamp - v > 11_000) {
                    keys.add(k);
                }
            });
            keys.forEach(f -> status.remove(f));
        }
        synchronized (runInfos) {
            List<Integer> keys = new ArrayList<>();
            runInfos.forEach((k, v) -> {
                if (k - timeStamp > 3_3600_000) {
                    keys.add(k);
                }
            });
            keys.forEach(f -> runInfos.remove(f));
        }
    }

    /**添加运行信息
     * @param bean
     */
    public static void addRunInfo(RunInfoBean bean) {
        synchronized (runInfos) {
            runInfos.computeIfAbsent(bean.getMark(), f->new ArrayList<RunInfoBean>());
            runInfos.get(bean.getMark()).add(bean);
        }
    }


    public static String getHost(String host) {
        return "http://" + host;
    }

}
