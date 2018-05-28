package com.example.dmitry.picturesviewer;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
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
import java.util.List;

public class GeneralActivity extends AppCompatActivity {

    public static final String LOG_MENU = "menu";
   public static final int REQUEST_IMAGE_CAPTURE = 0;
   public static final int IMAGE_REQUEST = 1;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        images = new ArrayList<>();

        setInitialData();

        listener = new PicturesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Image item) {
                Toast.makeText(getApplicationContext(),"Test Normal Click " + item.toString(),Toast.LENGTH_SHORT).show();
            }
        };
        listenerLong = new PicturesAdapter.OnItemLongClickListener() {
            @Override
            public boolean OnItemLongClick(Image item) {
                Toast.makeText(getApplicationContext(),"Image was deleted:  " + item.toString(),Toast.LENGTH_SHORT).show();
                images.remove(item);
                picturesAdapter.notifyDataSetChanged();
                return true;
            }
        };

        picturesAdapter = new PicturesAdapter(this,images,listener,listenerLong);
        gridLayoutManager = new GridLayoutManager(this,3);

        fab = (FloatingActionButton) findViewById(R.id.fabBtn_camera);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerListView);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(picturesAdapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"pv_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                i.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(i,REQUEST_IMAGE_CAPTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_IMAGE_CAPTURE){

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void getAllFiles(){
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        Log.d("path","Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("size","Size: " + files.length);
        for (int i = 0; i < files.length; i++){
            Log.d("files","File:" + files[i].getName());
        }
    }

//    public boolean deleteFile(String path){
//        File file = new File(path);
//        boolean deleted = file.delete();
//        return deleted;
//    }

    private void setInitialData(){
        images.add(new Image(R.drawable.android));
        images.add(new Image(R.drawable.ic_photo_camera_black_24dp));
        images.add(new Image(R.drawable.bigandroid));
    }
}
