package com.example.WallPic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView wallpaperView;
    Button set_Wallpaper_bt;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wallpaperView=findViewById(R.id.Wallpaper_image);
        set_Wallpaper_bt=findViewById(R.id.Set_wallpaper_button);
        WallpaperManager wallpaperManager=WallpaperManager.getInstance(getApplicationContext());
        Bitmap img = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.wallpaper));
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        Bitmap bitmap = Bitmap.createScaledBitmap(img, width, height, true);
        set_Wallpaper_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                 wallpaperManager.setBitmap(bitmap,null,false,1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}