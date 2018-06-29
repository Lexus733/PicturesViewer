package com.example.dmitry.picturesviewer.presentation.picturesview;

import android.widget.ImageView;

import com.example.dmitry.picturesviewer.R;
import com.example.dmitry.picturesviewer.data.ReposInternal;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PictureViewPresenter implements IPictureViewer.Presenter {

    private IPictureViewer.View view;
    private ReposInternal repo;


    public PictureViewPresenter(IPictureViewer.View view) {
        this.view = view;
        repo = new ReposInternal();
    }

    @Override
    public void setImage(String path, ImageView imageView) {
        if (view != null) {
            view.showProgress();
        }
        Picasso.get().load(new File(path)).error(R.drawable.ic_warning_black_24dp).placeholder(R.drawable.progress_animation).into(imageView);

        view.hideProgress();
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onFinished() {
    }

}
