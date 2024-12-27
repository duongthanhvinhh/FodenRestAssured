package org.foden.utils;

import java.util.HashMap;

public class DataManager {
    private static DataManager dataManager;
    private HashMap<String, String> dataMap;

    private DataManager() {
        dataMap = new HashMap<>();
    }

    public static synchronized DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    public void setData(String key, String value) {
        dataMap.put(key, value);
    }

    public String getData(String key) {
        return dataMap.get(key);
    }

    public HashMap<String, String> getAllData() {
        return dataMap;
    }
}

