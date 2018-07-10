package com.example.dmitry.picturesviewer.data;

import android.content.Intent;

import com.example.dmitry.picturesviewer.domain.Image;

import java.util.List;

public interface RepoInternalInterface {
    Intent createPhoto();

    void deleteFile(String path);

    List<Image> getData();
}
