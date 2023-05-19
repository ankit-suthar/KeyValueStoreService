package com.key.value.storage.service;

import com.key.value.storage.enums.Operation;
import com.key.value.storage.model.ValueObject;
import com.key.value.storage.persistance.PersistenceAccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.key.value.storage.constant.FileConstants.FILE_PATH;

public class RecoveryService {

    Map<String, Object> db;

    public RecoveryService() {
        this.db = PersistenceAccess.getInstance().getDb();
    }

    public void buildDbFromFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        String line = br.readLine();

        while(line!=null) {
            String[] inputStr = line.split("|");

            String key = inputStr[0];
            Map<Object, Object> map = convertStringToMap(inputStr[1]);
            Operation ops = Operation.valueOf(inputStr[2]);
            Long ts = Long.parseLong(inputStr[3]);


            ValueObject valueObject = new ValueObject();
            valueObject.setValueMapper(map);
            valueObject.setTimestamp(ts);

            if(Operation.ADD.equals(ops) || Operation.UPDATE.equals(ops)) {
                add(key, valueObject);
            } else {
                delete(key, ts);
            }

            line=br.readLine();
        }
    }


    private void add(String key, Object obj) {
        if(db.containsKey(key)) {
            ValueObject valueObject = (ValueObject) db.get(key);
            if(valueObject.getTimestamp()>=((ValueObject)obj).getTimestamp()) {
                return;
            }
        }
        db.put(key, obj);
    }

    public void delete(String key, Long timestamp) throws IOException {
        if(db.containsKey(key)) {
            ValueObject valueObject = (ValueObject) db.get(key);
            if(valueObject.getTimestamp()>=timestamp) {
                return;
            }
        }
        ValueObject valueObject = new ValueObject();
        valueObject.setValueMapper(null);
        valueObject.setTimestamp(timestamp);
        db.put(key, valueObject);
    }

    private Map<Object, Object> convertStringToMap(String inputStr) {
        String value = inputStr;
        value = value.substring(1, value.length()-1);
        String[] keyValuePairs = value.split(",");
        Map<Object,Object> map = new HashMap<>();

        for(String pair : keyValuePairs) {
            String[] entry = pair.split("=");
            map.put(entry[0].trim(), entry[1].trim());
        }

        return map;
    }
}
