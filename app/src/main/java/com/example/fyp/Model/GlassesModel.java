package com.example.fyp.Model;

public class GlassesModel {
    String id;
    String imgpath;
    String imgname;
    String price;
    String quantity;

    public GlassesModel()
    {

    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public GlassesModel(String id, String  imgpath, String imgname, String price, String quantity) {
        this.id = id;
        this.imgpath = imgpath;
        this.imgname = imgname;
        this.price = price;
        this.quantity = quantity;
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
