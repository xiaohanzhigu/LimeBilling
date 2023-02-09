package com.chenghan.keepaccounts.common;

import java.util.HashMap;

public class Data {
    public static HashMap map = new HashMap();

    static {
        putData("isFirstOpenApplication", true);
    }

    public static void putData(String k, Object v) {
        map.put(k, v);
    }

    public static Object getData(String k) {
        return map.get(k);
    }
}
