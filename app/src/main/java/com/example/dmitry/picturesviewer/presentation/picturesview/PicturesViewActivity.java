package com.example.dmitry.picturesviewer.presentation.picturesview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.dmitry.picturesviewer.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PicturesViewActivity extends AppCompatActivity implements IPictureViewer.View {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_view);
        imageView = findViewById(R.id.picturesView_viewer);
        PictureViewPresenter presenter = new PictureViewPresenter(this, getIntent());
    }

    @Override
    public void showPicture(String path) {
        Picasso.get()
                .load(new File(path))
                .error(R.drawable.ic_warning_black_24dp)
                .placeholder(R.drawable.progress_animation)
                .into(imageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
