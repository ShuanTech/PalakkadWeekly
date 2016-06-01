package com.shuan.palakkadweekly.lists;

/**
 * Created by shuan on 1/11/2016.
 */
public class GalleryList {

    public String id,img;

    public GalleryList() {
    }

    public GalleryList(String id, String img) {
        this.id = id;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
