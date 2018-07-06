package com.example.dmitry.picturesviewer.presentation.generalscreen;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dmitry.picturesviewer.R;
import com.example.dmitry.picturesviewer.domain.Image;

import java.util.List;

public class GeneralActivity extends AppCompatActivity implements IGeneralScreen.View {

    private List<Image> images;
    private RecyclerView recyclerView;
    private GeneralScreenPresenter presenter;
    private PicturesAdapter picturesAdapter;
    private AlertDialog deleteDialog;
    private FloatingActionButton fab;
    private GridLayoutManager gridLayoutManager;
    private Context context;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_general_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_by_date: {
                presenter.menuSortByDate(images);
                return true;
            }
            case R.id.action_sort_by_size: {
                presenter.menuSortBySize(images);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        setContentView(R.layout.activity_general);

        recyclerView = findViewById(R.id.recyclerListView);

        fab = findViewById(R.id.fabBtn_camera);

        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        presenter = new GeneralScreenPresenter(this);

        images = presenter.getAllFiles();

        picturesAdapter = new PicturesAdapter(images, presenter.getOnItemListener(), presenter.getLongListener());
        recyclerView.setAdapter(picturesAdapter);
        presenter.setPhotoListener();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog(Image image) {
        if (deleteDialog != null) {
            deleteDialog.dismiss();
            deleteDialog = null;
        } else {
            deleteDialog = createDeleteDialog(image);
            deleteDialog.show();
        }
    }

    private AlertDialog createDeleteDialog(final Image item) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.dialog_title);
        alertDialogBuilder.setMessage(R.string.dialog_message);
        alertDialogBuilder.setIcon(R.drawable.ic_warning_black_24dp);
        alertDialogBuilder.setPositiveButton(R.string.dialog_yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        presenter.deleteItem(item);
                    }
                }).setNegativeButton(R.string.dialog_cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
        return alertDialogBuilder.create();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter = new GeneralScreenPresenter(this);
        images = presenter.getAllFiles();
        picturesAdapter = new PicturesAdapter(images, presenter.getOnItemListener(), presenter.getLongListener());
        recyclerView.setAdapter(picturesAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }


    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setOnClickCreatePhoto(View.OnClickListener clickCreatePhoto) {
        fab.setOnClickListener(clickCreatePhoto);
    }

    @Override
    public List<Image> getListImage() {
        return images;
    }

    @Override
    public void refreshAdapter() {
        picturesAdapter.notifyDataSetChanged();
    }

}
