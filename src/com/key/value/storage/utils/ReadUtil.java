package com.key.value.storage.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

import static com.key.value.storage.constant.FileConstants.INPUT_PATH;

public class ReadUtil {

    BufferedReader br = new BufferedReader(new FileReader(INPUT_PATH));


    public static Map<String, Object> getInput() {

    }
}
