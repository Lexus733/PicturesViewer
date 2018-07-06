package com.example.dmitry.picturesviewer.data;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.dmitry.picturesviewer.domain.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReposInternal implements RepoInternalInterface {

    @Override
    public Intent createPhoto() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                , "pv_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
        i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return i;
    }

    @Override
    public void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    @Override
    public List<Image> getData() {
        List<Image> images = new ArrayList<>();

        final File[] files = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()).listFiles();

        for (File file : files) {

            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), file.getName());

            if (!file.isDirectory() && !file.isHidden()) {

                images.add(new Image(file.getAbsolutePath()));
            }
        }
        return images;
    }
}
