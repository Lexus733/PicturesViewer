package com.example.dmitry.picturesviewer.presentation.generalscreen;

import android.content.Context;

import com.example.dmitry.picturesviewer.domain.Image;

import java.util.List;

public interface IGeneralScreen {

    interface View {
        Context getContext();

        void setOnClickCreatePhoto(android.view.View.OnClickListener clickCreatePhoto);

        List<Image> getListImage();

        void refreshAdapter();

        void showMessage(String message);

        void showDialog(Image image);
    }
}
