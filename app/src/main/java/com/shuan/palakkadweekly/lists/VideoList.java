package com.shuan.palakkadweekly.lists;

/**
 * Created by shuan on 1/11/2016.
 */
public class VideoList {
    String heading,link;

    public VideoList() {
    }

    public VideoList(String heading, String link) {
        this.heading = heading;
        this.link = link;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
