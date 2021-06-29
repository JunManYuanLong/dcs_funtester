package dcs.common.basedata;

import com.funtester.utils.Time;

import java.util.concurrent.ConcurrentHashMap;

public class NodeData {

    public static ConcurrentHashMap<String, Boolean> status = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Long> time = new ConcurrentHashMap<>();


    public static void register(String host) {
        synchronized (status) {
            status.put(host, true);
            mark(host);
        }
    }

    private static void mark(String host) {
        time.put(host, Time.getTimeStamp());
    }

    public static void check() {
        synchronized (status) {
            long timeStamp = Time.getTimeStamp();
            time.forEach((k, v) -> {
                if (timeStamp - v > 10_000) {
                    status.remove(k);
                }
            });
        }
    }

    public static String getHost(String host) {
        return "http://" + host;
    }

}
