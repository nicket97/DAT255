package com.example.walling.elizaapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olofenstrom on 2017-09-15.
 */

public class DataSaver {

    private List<Integer> dataList;
    private int maxLength;

    public DataSaver() {
        dataList = new ArrayList<>();
        maxLength = 0;
    }

    public DataSaver(int maxLength) {
        dataList = new ArrayList<>();
        this.maxLength = maxLength;
    }

    public void addValue(int val) {
        if (maxLength == 0) {
            dataList.add(val);
        } else {
            if (dataList.size() >= maxLength) {
                removeLastVal();
            }
            dataList.add(val);
        }
    }

    public int getLatestVal() {
        return dataList.get(dataList.size()-1);
    }

    private void removeLastVal() {
        System.out.println("Removing last: " + dataList.get(0));
        dataList.remove(0);
    }

}
