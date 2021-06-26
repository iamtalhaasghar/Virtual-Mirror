package com.example.fyp.Model;

public class GlassesModel {
    String id;
    int imgpath;
    String imgname;

    public GlassesModel(String id, int imgpath, String imgname) {
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

    public int getImgpath() {
        return imgpath;
    }

    public void setImgpath(int imgpath) {
        this.imgpath = imgpath;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }
}
