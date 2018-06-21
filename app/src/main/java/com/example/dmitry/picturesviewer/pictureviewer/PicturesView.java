package com.example.dmitry.picturesviewer.pictureviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.dmitry.picturesviewer.R;
import com.example.dmitry.picturesviewer.pictureviewer.PictureViewPresenter;
import com.example.dmitry.picturesviewer.pictureviewer.PictureViewer;

public class PicturesView extends AppCompatActivity implements PictureViewer.View {

    private PictureViewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_view);

        presenter = new PictureViewPresenter(this);
        initView();
    }

    @Override
    public void initView() {
        final ImageView imageView = findViewById(R.id.picturesView_viewer);

        final Intent intent = getIntent();
        final String path = intent.getStringExtra("path");

        presenter.setImage(path, imageView);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
