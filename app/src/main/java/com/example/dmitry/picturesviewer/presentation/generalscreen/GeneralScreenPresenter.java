package com.example.dmitry.picturesviewer.presentation.generalscreen;

import android.content.Intent;
import android.view.View;

import com.example.dmitry.picturesviewer.data.ReposInternal;
import com.example.dmitry.picturesviewer.domain.Image;
import com.example.dmitry.picturesviewer.other.IntentKeys;
import com.example.dmitry.picturesviewer.presentation.picturesview.PicturesViewActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GeneralScreenPresenter {
    private IGeneralScreen.View view;
    private ReposInternal repo;
    private PicturesAdapter picturesAdapter;
    private List<Image> images;

    private boolean sortBySize;
    private boolean sortByDate;

    GeneralScreenPresenter(IGeneralScreen.View view) {
        this.view = view;
        this.repo = new ReposInternal();
        this.images = repo.getData();
        this.picturesAdapter = new PicturesAdapter(images, getOnItemListener(), getLongListener());
    }

    public void menuSortBySize() {
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

    public void menuSortByDate() {
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

    private PicturesAdapter.OnItemClickListener getOnItemListener() {
        return new PicturesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Image item) {
                Intent i = new Intent(view.getContext(), PicturesViewActivity.class);
                i.putExtra(IntentKeys.PATH_TO_PHOTO, item.getPath());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(i);
            }
        };
    }

    private PicturesAdapter.OnItemLongClickListener getLongListener() {
        return new PicturesAdapter.OnItemLongClickListener() {
            @Override
            public boolean OnItemLongClick(Image item) {
                view.showDialog(item);
                return true;
            }
        };
    }

    private View.OnClickListener createPhotoListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(repo.createPhoto());
            }
        };
    }

    public void setPhotoListener() {
        view.setOnClickCreatePhoto(createPhotoListener());
    }

    public void deleteItem(Image item) {
        images.remove(item);
        repo.deleteFile(item.getPath());
        picturesAdapter.notifyDataSetChanged();
    }

    public void onDestroy() {
        view = null;
    }

    public PicturesAdapter getPicturesAdapter() {
        return picturesAdapter;
    }
}
