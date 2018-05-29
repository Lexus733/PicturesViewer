package com.example.dmitry.picturesviewer;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Image {

    private Bitmap image;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Image(Bitmap image, String path){
        this.image = image;
        this.path = path;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
