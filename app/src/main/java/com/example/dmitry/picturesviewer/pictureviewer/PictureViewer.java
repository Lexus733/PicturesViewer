package com.example.dmitry.picturesviewer.pictureviewer;

import android.widget.ImageView;

public interface PictureViewer {

    interface View {
        void initView();

        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void setImage(String path, ImageView imageView);

        void onDestroy();

        void onFinished();
    }

    interface Repositories {

    }
}
