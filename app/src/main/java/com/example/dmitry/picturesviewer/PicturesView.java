package com.example.dmitry.picturesviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class PicturesView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_view);

        final ImageView imageView = findViewById(R.id.picturesView_viewer);
        final Intent intent = getIntent();
        final String path = intent.getStringExtra("path");

        Picasso.get().load(new File(path)).error(R.drawable.ic_warning_black_24dp).placeholder(R.drawable.progress_animation).into(imageView);
    }
}
