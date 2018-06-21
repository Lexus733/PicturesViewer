package com.example.dmitry.picturesviewer.pictureviewer;

import android.widget.ImageView;

import com.example.dmitry.picturesviewer.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PictureViewPresenter implements PictureViewer.Presenter {

    private PictureViewer.View view;
    private PictureViewRepositories repo;


    public PictureViewPresenter(PictureViewer.View view) {
        this.view = view;
        repo = new PictureViewRepositories();
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
