package com.example.dmitry.picturesviewer.presentation.generalscreen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.example.dmitry.picturesviewer.data.ReposInternal;
import com.example.dmitry.picturesviewer.domain.Image;
import com.example.dmitry.picturesviewer.presentation.picturesview.PicturesView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GeneralScreenPresenter implements IGeneralScreen.Presenter {

    private IGeneralScreen.View view;
    private ReposInternal repo;

    private boolean sortBySize;
    private boolean sortByDate;

    GeneralScreenPresenter(IGeneralScreen.View view) {

        this.view = view;
        this.repo = new ReposInternal();
    }

    @Override
    public void menuSortBySize(List<Image> images, PicturesAdapter picturesAdapter) {

        if (sortBySize) {
            view.showMessage("Sorted by size: Bigger ");

            Collections.sort(images, new Comparator<Image>() {
                @Override
                public int compare(Image o1, Image o2) {
                    if (o1.getSize() > o2.getSize()) {
                        return -1;
                    } else if (o1.getSize() < o2.getSize()) {
                        return 1;
                    }
                    return 0;
                }
            });

            sortBySize = !sortBySize;
            picturesAdapter.notifyDataSetChanged();

        } else {

            view.showMessage("Sorted by size: Smaller ");

            Collections.sort(images, new Comparator<Image>() {
                @Override
                public int compare(Image o1, Image o2) {
                    if (o1.getSize() > o2.getSize()) {
                        return 1;
                    } else if (o1.getSize() < o2.getSize()) {
                        return -1;
                    }
                    return 0;
                }
            });

            sortBySize = !sortBySize;
            picturesAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void menuSortByDate(List<Image> images, PicturesAdapter picturesAdapter) {

        if (sortByDate) {

            view.showMessage("Sorted by date: Newer ");

            Collections.sort(images, new Comparator<Image>() {
                @Override
                public int compare(Image o1, Image o2) {
                    if (o1.getDate().getTime() > o2.getDate().getTime()) {
                        return -1;
                    } else if (o1.getDate().getTime() < o2.getDate().getTime()) {
                        return 1;
                    }
                    return 0;
                }
            });

            sortByDate = !sortByDate;
            picturesAdapter.notifyDataSetChanged();

        } else {

            view.showMessage("Sorted by date: Older ");

            Collections.sort(images, new Comparator<Image>() {
                @Override
                public int compare(Image o1, Image o2) {
                    if (o1.getDate().getTime() > o2.getDate().getTime()) {
                        return 1;
                    } else if (o1.getDate().getTime() < o2.getDate().getTime()) {
                        return -1;
                    }
                    return 0;
                }
            });

            sortByDate = !sortByDate;
            picturesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public Intent onItemClick(Image item, Context context) {
        Intent i = new Intent(context, PicturesView.class);
        i.putExtra("path", item.getPath());
        return i;
    }

    @Override
    public void deleteItem(String path, Image item, List<Image> images, PicturesAdapter picturesAdapter) {
        images.remove(item);
        repo.deleteFile(path);
        picturesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLongCLick(AlertDialog.Builder showingDialog) {
        showingDialog.show();
    }

    @Override
    public Intent onFabButtonClick() {
        return repo.createPhoto();
    }


    @Override
    public List<Image> getAllFiles() {
        return repo.getData();
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
