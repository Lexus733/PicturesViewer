package com.example.dmitry.picturesviewer.generalscreen;

import android.content.Context;
import android.content.Intent;

import com.example.dmitry.picturesviewer.model.Image;

import java.util.List;

public interface GeneralScreen {

    interface View {
        void initView();

        void showProgress();

        void hideProgress();

        void showMessage(String message);

        void showDeleteDialog(String path, Image item);
    }

    interface Presenter {
        void menuSortBySize(List<Image> images);

        void menuSortByDate(List<Image> images);

        Intent onItemClick(Image item, Context context);

        void deleteItem(String path, Image item, List<Image> images);

        Intent onFabButtonClick();

        List<Image> getAllFiles();

        void onDestroy();

        void onFinished();
    }

    interface Repositories {
        Intent createPhoto();

        void deleteFile(String path);

        List<Image> getData();

    }
}
