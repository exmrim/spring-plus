package org.example.expert.aop;

import org.example.expert.aop.entity.Log;

//Lv3-11.Transaction 심화
public class LogContext {

    private static final ThreadLocal<Log> threadLocal = new ThreadLocal<>();

    public static void set(Log log) {
        threadLocal.set(log);
    }

    public static Log get() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
