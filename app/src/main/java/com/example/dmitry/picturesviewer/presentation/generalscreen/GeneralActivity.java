package com.example.dmitry.picturesviewer.presentation.generalscreen;

import android.content.Context;
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

public class GeneralActivity extends AppCompatActivity implements IGeneralScreen.View {
    private RecyclerView recyclerView;
    private GeneralScreenPresenter presenter;
    private AlertDialog deleteDialog;
    private FloatingActionButton fab;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_general);

        recyclerView = findViewById(R.id.recyclerListView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        presenter = new GeneralScreenPresenter(this);
        recyclerView.setAdapter(presenter.getPicturesAdapter());

        fab = findViewById(R.id.fabBtn_camera);
        presenter.setPhotoListener();
    }

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
                presenter.menuSortByDate();
                return true;
            }
            case R.id.action_sort_by_size: {
                presenter.menuSortBySize();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showMessage(Integer id) {
        Toast.makeText(this, getResources().getString(id), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog(Image image) {
        if (deleteDialog != null) {
            deleteDialog.dismiss();
            deleteDialog = null;
            deleteDialog = createDeleteDialog(image);
            deleteDialog.show();
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
                (arg0, arg1) -> presenter.deleteItem(item)).setNegativeButton(R.string.dialog_cancel,
                (arg0, arg1) -> arg0.dismiss());
        return alertDialogBuilder.create();
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
    protected void onResume() {
        super.onResume();
        presenter = new GeneralScreenPresenter(this);
        recyclerView.setAdapter(presenter.getPicturesAdapter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

}
