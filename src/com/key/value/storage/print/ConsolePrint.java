package com.key.value.storage.print;

public class ConsolePrint implements Print {

    @Override
    public void printData(String data) {
        System.out.println(data);
    }
}
