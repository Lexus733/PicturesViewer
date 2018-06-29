package com.example.dmitry.picturesviewer.presentation.picturesview;

import android.widget.ImageView;

public interface IPictureViewer {

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
}
