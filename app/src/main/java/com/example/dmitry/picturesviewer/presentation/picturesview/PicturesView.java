package com.example.dmitry.picturesviewer.presentation.picturesview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.dmitry.picturesviewer.R;

public class PicturesView extends AppCompatActivity implements IPictureViewer.View {

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

        presenter.setImage(intent.getStringExtra("path"), imageView);
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
