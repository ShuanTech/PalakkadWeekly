package com.shuan.palakkadweekly.lists;


public class BusDetailsList {
    String busName, frm, frmTime, to, toTime, trvlTime, src, dest;

    public BusDetailsList() {
    }

    public BusDetailsList(String frm, String to, String trvlTime) {
        this.frm = frm;
        this.to = to;
        this.trvlTime = trvlTime;
    }

    public BusDetailsList(String busName, String frm, String frmTime, String to, String toTime, String trvlTime, String src, String dest) {
        this.busName = busName;
        this.frm = frm;
        this.frmTime = frmTime;
        this.to = to;
        this.toTime = toTime;
        this.trvlTime = trvlTime;
        this.src = src;
        this.dest = dest;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getFrm() {
        return frm;
    }

    public void setFrm(String frm) {
        this.frm = frm;
    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrmTime() {
        return frmTime;
    }

    public void setFrmTime(String frmTime) {
        this.frmTime = frmTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getTrvlTime() {
        return trvlTime;
    }

    public void setTrvlTime(String trvlTime) {
        this.trvlTime = trvlTime;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }
}
