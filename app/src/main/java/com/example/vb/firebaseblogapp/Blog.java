package com.example.vb.firebaseblogapp;

/**
 * Created by vb on 16.03.2017.
 */

public class Blog {

    String title,desc,image;

    public Blog() {
    }

    public Blog(String title, String desc, String image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
