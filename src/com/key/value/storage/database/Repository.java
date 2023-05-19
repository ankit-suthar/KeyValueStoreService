package com.key.value.storage.database;

import java.util.HashMap;
import java.util.Map;

public class Repository {

    private final Map<String, Object> db;

    public Repository() {
        this.db = new HashMap<>();
    }

    public Map<String, Object> getDb() {
        return db;
    }
}
