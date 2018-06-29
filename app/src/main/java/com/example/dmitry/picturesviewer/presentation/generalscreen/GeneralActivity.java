package com.example.dmitry.picturesviewer.presentation.generalscreen;

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

    private GeneralScreenPresenter presenter;

    private PicturesAdapter picturesAdapter;
    private List<Image> images;


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
                picturesAdapter.notifyDataSetChanged();
                return true;
            }
            case R.id.action_sort_by_size: {
                presenter.menuSortBySize(images);
                picturesAdapter.notifyDataSetChanged();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        presenter = new GeneralScreenPresenter(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_general);

        initView();

    }

    @Override
    public void initView() {
        images = presenter.getAllFiles();

        final RecyclerView recyclerView = findViewById(R.id.recyclerListView);
        final FloatingActionButton fab = findViewById(R.id.fabBtn_camera);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        final PicturesAdapter.OnItemClickListener listener = new PicturesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Image item) {
                startActivity(presenter.onItemClick(item, getApplicationContext()));
            }
        };
        final PicturesAdapter.OnItemLongClickListener longClickListener = new PicturesAdapter.OnItemLongClickListener() {
            @Override
            public boolean OnItemLongClick(Image item) {
                showDeleteDialog(item.getPath(), item);
                return true;
            }
        };

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(presenter.onFabButtonClick());
            }
        });

        picturesAdapter = new PicturesAdapter(this, images, listener, longClickListener);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(picturesAdapter);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDeleteDialog(final String path, final Image item) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.dialog_title);
        alertDialogBuilder.setMessage(R.string.dialog_message);
        alertDialogBuilder.setIcon(R.drawable.ic_warning_black_24dp);
        alertDialogBuilder.setPositiveButton(R.string.dialog_yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        presenter.deleteItem(path, item, images);
                        picturesAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton(R.string.dialog_cancel,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
