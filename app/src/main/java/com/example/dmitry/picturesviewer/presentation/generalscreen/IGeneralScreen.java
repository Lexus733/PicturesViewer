package com.example.dmitry.picturesviewer.presentation.generalscreen;

import android.content.Context;

import com.example.dmitry.picturesviewer.domain.Image;

public interface IGeneralScreen {
    interface View {
        Context getContext();

        void setOnClickCreatePhoto(android.view.View.OnClickListener clickCreatePhoto);

        void showMessage(String message);

        void showDialog(Image image);
    }
}
