package com.game.qt.socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xf on 19/12/2017.
 */

public final class CallBackSingleton {
    private static CallBackSingleton instance = null;

    private static Map<String, Object> objMap = new HashMap<>();

    private CallBackSingleton(){

    }

    public static CallBackSingleton getInstance(){
        if (instance == null) {
            synchronized (CallBackSingleton.class) {
                if (instance == null) {
                    instance = new CallBackSingleton();
                }
            }
        }
        return instance;
    }

    public static void addObject(String key, Object obj) {
        if (objMap.containsKey(key)) {
            objMap.remove(key);
        }
        objMap.put(key, obj);
    }

    public static Object getObject(String key) {
        if (objMap.containsKey(key)) {
            return objMap.get(key);
        }
        return null;
    }

}
