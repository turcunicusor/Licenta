package com.smarthome.server.hal.Generic;

import java.util.ArrayList;
import java.util.Arrays;

public class Data extends ArrayList<String> {
    private static String separator = ";";

    public Data(ArrayList<String> data){
        this.clear();
        this.addAll(data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String part : this)
            sb.append(part).append(separator);
        return sb.toString();
    }

    public void addData(String data) {
        if (data.isEmpty()) return;
        this.clear();
        this.addAll(Arrays.asList(data.split(separator)));
    }
}
