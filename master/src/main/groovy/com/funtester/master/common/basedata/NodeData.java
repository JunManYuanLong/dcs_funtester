package com.funtester.master.common.basedata;

import com.funtester.base.bean.PerformanceResultBean;
import com.funtester.base.exception.FailException;
import com.funtester.frame.SourceCode;
import com.funtester.master.common.bean.manager.RunInfoBean;
import com.funtester.master.manaer.MasterManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class NodeData {

    private static final Logger logger = LogManager.getLogger(NodeData.class);

    /**
     * 节点状态
     */
    public static ConcurrentHashMap<String, Boolean> status = new ConcurrentHashMap<>();

    /**
     * 节点的运行信息,通过progress获取
     */
    public static ConcurrentHashMap<String, String> runInfos = new ConcurrentHashMap<>();

    /**
     * 节点的运行结果
     */
    public static LinkedHashMap<Integer, List<PerformanceResultBean>> results = new LinkedHashMap<>();

    /**
     * 节点运行的任务id
     */
    public static ConcurrentHashMap<String, Integer> tasks = new ConcurrentHashMap<>();

    /**
     * 注册更新
     *
     * @param host
     * @param s
     */
    public static void register(String host, boolean s) {
        synchronized (status) {
            status.put(host, s);
        }
        logger.info("节点: {} 注册成功!", host);
    }

    /**
     * 可用节点
     *
     * @return
     */
    public static List<String> available() {
        synchronized (status) {
            List<String> availables = new ArrayList<>();
            status.forEach((k, v) -> {
                if (v) availables.add(k);
            });
            return availables;
        }
    }

    /**
     * 检查,删除过期节点和过期数据,提供定时任务执行
     */
    public static void check() {
        int timeStamp = SourceCode.getMark();
        synchronized (results) {
            List<Integer> tkeys = new ArrayList<>();
            results.forEach((k, v) -> {
                if (timeStamp - k > 3 * 3600) {
                    tkeys.add(k);
                }
            });
            tkeys.forEach(f -> results.remove(f));
        }
        synchronized (tasks) {
            tasks.forEach((k, v) -> {
                if (timeStamp - v > 60 * 30) tasks.put(k, 0);
            });
        }
    }

    /**
     * 单独设置检查节点
     */
    public static void checkNode() {
        List<String> hkeys = new ArrayList<>();
        synchronized (status) {
            status.entrySet().forEach(f -> {
                boolean alive = MasterManager.alive(f.getKey());
                if (!alive) hkeys.add(f.getKey());
            });
            hkeys.forEach(f -> {
                status.remove(f);
                logger.warn("节点失效: {}", f);
            });
        }
        synchronized (runInfos) {
            hkeys.forEach(f -> runInfos.remove(f));
        }
        synchronized (tasks) {
            hkeys.forEach(f -> tasks.remove(f));
        }
    }

    /**
     * 添加运行信息
     *
     * @param bean
     */
    public static void addRunInfo(RunInfoBean bean) {
        synchronized (runInfos) {
            logger.info("更新成功 {} , {}",bean.getHost(),bean.getRuninfo());
            runInfos.put(bean.getHost(), bean.getRuninfo());
        }
    }

    /**
     * 获取描述的的用例任务运行信息
     *
     * @param desc 任务描述信息
     * @return
     */
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

    /**
     * 添加节点运行任务id
     *
     * @param host
     * @param mark
     */
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
            if (num < 1 || num > available.size())
                FailException.fail("没有足够节点执行任务");
            List<String> nods = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                String random = SourceCode.random(available);
                status.put(random, false);
                nods.add(random);
            }
            return nods;
        }


    }

}
