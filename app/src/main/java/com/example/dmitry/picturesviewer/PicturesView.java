package com.example.dmitry.picturesviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class PicturesView extends AppCompatActivity {

    ImageView imageView;
    String path;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_view);
        imageView = (ImageView) findViewById(R.id.picturesView_viewer);
        intent = getIntent();
        path = intent.getStringExtra("path");
        Picasso.get().load(new File(path)).error(R.drawable.ic_warning_black_24dp).placeholder(R.drawable.progress_animation).into(imageView);
    }
}
