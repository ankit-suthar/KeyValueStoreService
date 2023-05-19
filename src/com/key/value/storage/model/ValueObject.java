package com.key.value.storage.model;

import java.util.*;

public class ValueObject {

    Map<Object, Object> valueMapper;
    Long timestamp; // Epoch

    public Map<Object, Object> getValueMapper() {
        return valueMapper;
    }

    public void setValueMapper(Map<Object, Object> valueMapper) {
        this.valueMapper = valueMapper;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
