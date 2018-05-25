package com.example.dmitry.picturesviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class PicturesView extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_view);
        imageView = (ImageView) findViewById(R.id.picturesView_viewer);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK ){
            if (requestCode == GeneralActivity.IMAGE_REQUEST){

                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("image");
                imageView.setImageBitmap(bitmap);
            }
        } else{
            super.onActivityResult(requestCode, resultCode, data);
        }



    }
}
