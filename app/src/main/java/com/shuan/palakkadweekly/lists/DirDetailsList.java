package com.shuan.palakkadweekly.lists;

/**
 * Created by shuan on 1/11/2016.
 */
public class DirDetailsList {
    public String id,name,address,phone,weburl,cover_photo;

    public DirDetailsList() {
    }

    public DirDetailsList(String id, String name, String address, String phone, String weburl, String cover_photo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.weburl = weburl;
        this.cover_photo = cover_photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }
}
