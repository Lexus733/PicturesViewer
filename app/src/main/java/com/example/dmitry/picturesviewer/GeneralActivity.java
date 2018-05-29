package com.example.dmitry.picturesviewer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
    private PicturesAdapter.OnItemClickListener listenerDelete;
    private List<Image> images;
    private DeleteDialogFragment deleteDialogFragment;


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

                deleteDialogFragment = new DeleteDialogFragment();

                Bundle args = new Bundle();
                args.putString("path",item.getPath());
                deleteDialogFragment.setArguments(args);
                deleteDialogFragment.show(getFragmentManager(),"custom");

                        images.remove(item);
                        picturesAdapter.notifyDataSetChanged();

                return true;
            }
        };

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"pv_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                i.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(i,REQUEST_IMAGE_CAPTURE);
            }
        });


        picturesAdapter = new PicturesAdapter(this,images,listener,listenerLong);
        gridLayoutManager = new GridLayoutManager(this,3);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(picturesAdapter);
    }

    private void getAllFiles(){

        images = new ArrayList<>();

        File file = null;
        final int pix = getResources().getDimensionPixelSize(R.dimen.recyclerViewer_size);
        final File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());
        final File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {

            Log.d("files", "File:" + files[i].getName());

            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), files[i].getName());

            if (!file.isDirectory() && !file.isHidden()) {
                Bitmap bitmap = decodeSampledBitmapFromResource(file.getAbsolutePath(), pix, pix);
                images.add(new Image(bitmap,files[i].getAbsolutePath()));

            }
        }
    }


    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

//    public boolean deleteFile(String path){
//        File file = new File(path);
//        boolean deleted = file.delete();
//        return deleted;
//    }

}
