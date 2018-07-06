package com.example.dmitry.picturesviewer.presentation.picturesview;

import android.content.Intent;

import com.example.dmitry.picturesviewer.other.IntentKeys;

public class PictureViewPresenter {
    PictureViewPresenter(IPictureViewer.View view, Intent intent) {
        view.showPicture(intent.getStringExtra(IntentKeys.PATH_TO_PHOTO));
    }

}
