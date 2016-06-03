package com.shuan.palakkadweekly.lists;

/**
 * Created by shuan on 1/9/2016.
 */
public class HomeList {
    public String id, heading, img, dt;

    public HomeList() {
    }

    public HomeList(String id, String heading, String img, String dt) {
        this.id = id;
        this.heading = heading;
        this.img = img;
        this.dt = dt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }
}
