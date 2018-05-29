package com.example.dmitry.picturesviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class PicturesView extends AppCompatActivity {

    ImageView imageView;
    String path;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_view);
        imageView = (ImageView) findViewById(R.id.picturesView_viewer);
        intent = getIntent();
        path = intent.getStringExtra("path");
        getFile(path);
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

    private void getFile(String path){
        File file = new File(path);
    Bitmap bitmap = decodeSampledBitmapFromResource(file.getAbsolutePath(),imageView.getMaxWidth(),imageView.getMaxHeight());
    imageView.setImageBitmap(bitmap);
    }

}
