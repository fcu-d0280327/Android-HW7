package com.example.user.android_hw7;

import android.graphics.Bitmap;

/**
 * Created by user on 2017/6/6.
 */

public class Hotel {
    Bitmap image;
    String name;
    String address;

    public Hotel(){}

    public Hotel(Bitmap image, String name, String address){
        this.address = address;
        this.name = name;
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
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
}
