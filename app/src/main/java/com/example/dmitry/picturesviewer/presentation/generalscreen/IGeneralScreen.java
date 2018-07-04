package com.example.dmitry.picturesviewer.presentation.generalscreen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.example.dmitry.picturesviewer.domain.Image;

import java.util.List;

public interface IGeneralScreen {

    interface View {
        void initView();

        void showMessage(String message);

        AlertDialog.Builder createDeleteDialog(String path, Image item);
    }

    interface Presenter {
        void menuSortBySize(List<Image> images, PicturesAdapter picturesAdapter);

        void menuSortByDate(List<Image> images, PicturesAdapter picturesAdapter);

        Intent onItemClick(Image item, Context context);

        void deleteItem(String path, Image item, List<Image> images, PicturesAdapter picturesAdapter);

        void onLongCLick(AlertDialog.Builder showingDialog);

        Intent onFabButtonClick();

        List<Image> getAllFiles();

        void onDestroy();
    }
}
