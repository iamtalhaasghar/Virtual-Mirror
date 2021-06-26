package com.example.fyp.Model;

public class HatModel {
    String id;
    String imgpath;
    String imgname;

    public HatModel(String id, String imgpath, String imgname) {
        this.id = id;
        this.imgpath = imgpath;
        this.imgname = imgname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }
}
