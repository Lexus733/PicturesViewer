package com.example.dmitry.picturesviewer.model;

import android.graphics.Bitmap;

import java.io.File;
import java.util.Date;

public class Image {


    private Bitmap image;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getSize() {
        return (double) (new File(getPath()).length() / (1024 * 1024));
    }

    public Date getDate() {
        return new Date((new File(getPath()).lastModified()));
    }


    public Image(String path) {
        this.path = path;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


}
