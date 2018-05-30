package com.example.dmitry.picturesviewer;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GeneralActivity extends AppCompatActivity {

    public static final String LOG_MENU = "menu";

    private Uri imageUri;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private PicturesAdapter picturesAdapter;
    private PicturesAdapter.OnItemClickListener listener;
    private PicturesAdapter.OnItemLongClickListener listenerLong;
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
        switch (id){
            case R.id.action_sort_by_date:{
                Log.d(LOG_MENU,"Sort by date pressed" + id);
                Toast.makeText(getApplicationContext(),"By date",Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.action_sort_by_size:{
            Log.d(LOG_MENU,"Sort by size pressed" + id);
                Toast.makeText(getApplicationContext(),"By size",Toast.LENGTH_SHORT).show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        images = new ArrayList<>();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_general);

        getAllFiles();

        fab = (FloatingActionButton) findViewById(R.id.fabBtn_camera);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerListView);


        listener = new PicturesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Image item) {
                Intent i = new Intent(getApplicationContext(),PicturesView.class);
                i.putExtra("path",item.getPath());
                startActivity(i);
            }
        };

        listenerLong = new PicturesAdapter.OnItemLongClickListener() {
            @Override
            public boolean OnItemLongClick(Image item) {
                        showDeleteItemDialog(item.getPath(),item);
                return true;
            }
        };

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"pv_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                i.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivity(i);
            }
        });


        picturesAdapter = new PicturesAdapter(this,images,listener,listenerLong);
        gridLayoutManager = new GridLayoutManager(this,3);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(picturesAdapter);
    }


    private void getAllFiles(){

        File file = null;
        final int pix = getResources().getDimensionPixelSize(R.dimen.recyclerViewer_size);
        final File[] files = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()).listFiles();

        for (int i = 0; i < files.length; i++) {

            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), files[i].getName());

            if (!file.isDirectory() && !file.isHidden()) {

                images.add(new Image(files[i].getAbsolutePath()));
               Log.d("files", "File:" + files[i].getName() + "Size: " + (new File(files[i].getAbsolutePath()).length()/(1024*1024))+ " Mb " + " Date: " + new Date(files[i].lastModified()).toString());
            }
        }
    }

    public void showDeleteItemDialog(final String path, final Image item) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.dialog_title);
        alertDialogBuilder.setMessage(R.string.dialog_message);
        alertDialogBuilder.setPositiveButton(R.string.dialog_yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        File file = new File(path);
                        file.delete();
                        images.remove(item);
                        picturesAdapter.notifyDataSetChanged();
                    }
                });

        alertDialogBuilder.setNegativeButton(R.string.dialog_cancel,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
