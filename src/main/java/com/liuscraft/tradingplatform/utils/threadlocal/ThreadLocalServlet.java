package com.liuscraft.tradingplatform.utils.threadlocal;

import com.liuscraft.tradingplatform.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiusCraft
 * @date 2023/3/5 0:06
 */
public class ThreadLocalServlet {
    public static final Editor editor = new Editor();
    public static class Editor {
        public Editor setValue(String key, Object value) {
            getLocalValue().put(key, value);
            return this;
        }
        public Editor isAdmin() {
            getLocalValue().put("isadmin", true);
            return this;
        }
    }
    private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    private static Map<String, Object> getLocalValue() {
        if (threadLocal.get() == null) threadLocal.set(new HashMap<>());
        return threadLocal.get();
    }

    public static <T> T getValue(String key) {
        Map<String, Object> stringObjectMap = getLocalValue();
        Object o = stringObjectMap.get(key);
        if (o == null) return  null;
        return (T) o;
    }
    public static void clear() {
        threadLocal.remove();
    }

    public static Integer getUserId() {
        Object userId = getValue("userId");
        if (userId == null) return -1;
        return Integer.valueOf(userId.toString());
    }
    public static Boolean isAdmin() {
        Object admin = getValue("isadmin");
        if (admin == null) return false;
        return Boolean.valueOf(admin.toString());
    }
}
