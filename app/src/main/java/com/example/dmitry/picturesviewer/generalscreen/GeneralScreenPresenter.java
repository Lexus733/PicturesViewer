package com.example.dmitry.picturesviewer.generalscreen;

import android.content.Context;
import android.content.Intent;

import com.example.dmitry.picturesviewer.model.Image;
import com.example.dmitry.picturesviewer.pictureviewer.PicturesView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GeneralScreenPresenter implements GeneralScreen.Presenter {

    private GeneralScreen.View view;
    private GeneralScreenRepositories repo;

    private boolean sortBySize;
    private boolean sortByDate;

    public GeneralScreenPresenter(GeneralScreen.View view) {

        this.view = view;
        this.repo = new GeneralScreenRepositories();
    }

    @Override
    public void menuSortBySize(List<Image> images) {

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
        }

    }

    @Override
    public void menuSortByDate(List<Image> images) {

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
        }
    }

    @Override
    public Intent onItemClick(Image item, Context context) {
        Intent i = new Intent(context, PicturesView.class);
        i.putExtra("path", item.getPath());
        return i;
    }

    @Override
    public void deleteItem(String path, Image item, List<Image> images) {
        images.remove(item);
        repo.deleteFile(path);
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

    @Override
    public void onFinished() {

    }
}
