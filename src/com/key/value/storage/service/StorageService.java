package com.key.value.storage.service;

import com.key.value.storage.enums.Operation;
import com.key.value.storage.exceptions.InvalidDataFormatException;
import com.key.value.storage.exceptions.KeyNotFoundException;
import com.key.value.storage.model.ValueObject;
import com.key.value.storage.persistance.PersistenceAccess;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static com.key.value.storage.constant.FileConstants.FILE_PATH;

public class StorageService {
    Map<String, Object> map;

    public StorageService() {
        this.map = PersistenceAccess.getInstance().getDb();
    }

    public void add(String key, Object obj) throws IOException {
        if(!(obj instanceof ValueObject)) {
            throw new InvalidDataFormatException();
        }

        appendInFile(key, obj, Operation.ADD);
        synchronized (this) {
            map.put(key, obj);
        }
    }

    public Object read(String key) {
        if(!map.containsKey(key)) {
            throw new KeyNotFoundException();
        }

        return map.get(key);
    }

    public void update(String key, Object obj) throws IOException {
        if(!(obj instanceof ValueObject)) {
            throw new InvalidDataFormatException();
        }

        appendInFile(key, obj, Operation.UPDATE);
        synchronized (this) {
            map.put(key, obj);
        }
    }

    public void delete(String key, Long timestamp) throws IOException {
        /**
         * Conditional in case of thread priority
         * simply add null value if record doesn't exist with timestamp
         * And remove below condition
         */
        if(!map.containsKey(key)) {
            throw new KeyNotFoundException();
        }

        Object obj = map.get(key);
        ValueObject value = (ValueObject) obj;
        value.setValueMapper(null);
        value.setTimestamp(timestamp);

        appendInFile(key, obj, Operation.DELETE);

        map.put(key, value);
    }

    private void appendInFile(String key, Object obj, Operation ops) throws IOException {
        ValueObject value = (ValueObject) obj;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH,true));
            bw.write(createContent(key, value, ops));
            bw.close();
        } catch (IOException e) {
            // relevant logs
            throw e;
        }
    }

    private String createContent(String key, ValueObject value, Operation ops) {
        return key +"|"+value.getValueMapper().toString()+"|"+ops.toString()+"|"+value.getTimestamp().toString();
    }
}
