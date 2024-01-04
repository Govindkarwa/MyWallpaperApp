package com.example.WallPic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;

public class SetDownloadWallpaperActivity extends AppCompatActivity {
    ImageView wallpaperImg;
    MaterialButton setWallpaper ,setOnHomeScreen,setOnLockScreen,setOnBoth;
    ImageButton clear_BT;
    com.google.android.material.progressindicator.CircularProgressIndicator loadingPB;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_download_wallpaper);
        wallpaperImg=findViewById(R.id.Download_wallpaper_img);
        setWallpaper=findViewById(R.id.Download_set_wallpaper);
        loadingPB=findViewById(R.id.loadingPB);
        loadingPB.hide();
        if(!checkPermission()){
            requestPermission();
        }
        data=getIntent().getStringExtra("Data");
        Dialog dialog = new Dialog(SetDownloadWallpaperActivity.this);
        //Image Path To Bitmap
        Bitmap img = BitmapFactory.decodeFile(String.valueOf(Uri.parse(data)));
        wallpaperImg.setImageBitmap(img);

        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.custom_set_wallpaper_dialog_box);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCancelable(true);
                setOnHomeScreen=dialog.findViewById(R.id.SetOnHomeScreen_Bt);
                setOnLockScreen=dialog.findViewById(R.id.SetOnLockScreen_Bt);
                setOnBoth=dialog.findViewById(R.id.SetOnBth_Bt);
                clear_BT=dialog.findViewById(R.id.clearDB_Bt);
                setOnHomeScreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean ans=SetWallpaper(1,img);
                        loadingPB.show();
                         dialog.hide();
                        if(ans){
                            loadingPB.hide();
                            dialog.dismiss();
                        }
                        Toast.makeText(SetDownloadWallpaperActivity.this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
                    }
                });
                setOnLockScreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean ans=SetWallpaper(2,img);
                        loadingPB.show();
                        dialog.hide();
                        if(ans){
                            loadingPB.hide();
                            dialog.dismiss();
                        }
                        Toast.makeText(SetDownloadWallpaperActivity.this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
                    }
                });
                setOnBoth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean ans=SetWallpaper(3,img);
                        loadingPB.show();
                        dialog.hide();
                        if(ans){
                            loadingPB.hide();
                            dialog.dismiss();
                        }
                        Toast.makeText(SetDownloadWallpaperActivity.this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
                    }
                });
                clear_BT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    //Set Wallpaper Code
    public Boolean SetWallpaper(int value,Bitmap img){
        WallpaperManager wallpaperManager=WallpaperManager.getInstance(getApplicationContext());
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        Bitmap bitmap = Bitmap.createScaledBitmap(img, width, height, true);
        try {
            wallpaperManager.setBitmap(bitmap,null,false,value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            return true;
        }

    }
    public boolean checkPermission(){
        int ReadImages= ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_MEDIA_IMAGES);
        return (ReadImages== PackageManager.PERMISSION_GRANTED);
    }
    private  void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_MEDIA_IMAGES}, AppData.RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==AppData.RequestPermissionCode && grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"Permission not Granted",Toast.LENGTH_LONG).show();
                sendToSettingDialog();
            }
        }
        else {
            Toast.makeText(this,"Permission not Granted",Toast.LENGTH_LONG).show();
            sendToSettingDialog();
        }
    }
    // Dialog Box To Application Setting For Enable Permission
    public void sendToSettingDialog(){
        new AlertDialog.Builder(SetDownloadWallpaperActivity.this)
                .setTitle("Alert for Permission")
                .setMessage("Go TO Setting For Permission")
                .setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                }).show();
    }
}