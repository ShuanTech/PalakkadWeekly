package com.shuan.palakkadweekly.lists;

/**
 * Created by shuan on 1/21/2016.
 */
public class BusCodeList {
    public String id,busCode,name;

    public BusCodeList() {
    }

    public BusCodeList(String id, String busCode, String name) {
        this.id = id;
        this.busCode = busCode;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
