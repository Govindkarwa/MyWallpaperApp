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
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class SetWallpaperActivity extends AppCompatActivity {
    ImageButton setWallpaper_bt,downloadWallpaper_bt;
    MaterialButton setOnHomeScreen,setOnLockScreen,setOnBoth;
    com.google.android.material.progressindicator.CircularProgressIndicator loadingPB;
    ImageView wallpaperImg;
    String imgUrl;
    ImageButton clear_BT;
    Integer imageNumber;

    Bitmap image = null;
    String logMsg="This Msg In SetWallpaperActivity ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wallpaper);
        imgUrl=getIntent().getStringExtra("URL");
        imageNumber=getIntent().getIntExtra("ImgNo",0000000);
        setWallpaper_bt=findViewById(R.id.SetWallpaper_bt);
        downloadWallpaper_bt=findViewById(R.id.DownloadWallpaper_bt);
        wallpaperImg=findViewById(R.id.Wallpaper_img);
        loadingPB=findViewById(R.id.loadingPB);
        loadingPB.hide();
        Glide.with(SetWallpaperActivity.this).load(imgUrl).into(wallpaperImg);
        Dialog dialog = new Dialog(SetWallpaperActivity.this);
        setWallpaper_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.custom_set_wallpaper_dialog_box);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCancelable(false);
                setOnHomeScreen=dialog.findViewById(R.id.SetOnHomeScreen_Bt);
                setOnLockScreen=dialog.findViewById(R.id.SetOnLockScreen_Bt);
                setOnBoth=dialog.findViewById(R.id.SetOnBth_Bt);
                clear_BT=dialog.findViewById(R.id.clearDB_Bt);
                setOnHomeScreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SetWallpaperTask myTask=new SetWallpaperTask(1);
                        myTask.execute();
                        dialog.dismiss();
                    }
                });
                setOnLockScreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SetWallpaperTask myTask=new SetWallpaperTask(2);
                        myTask.execute();
                        dialog.dismiss();
                    }
                });
                setOnBoth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SetWallpaperTask myTask=new SetWallpaperTask(3);
                        myTask.execute();
                        dialog.dismiss();

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


        downloadWallpaper_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()){
                    MyDownloadTask myTask = new MyDownloadTask();
                    myTask.execute();
                }else {
                    requestPermission();
                }


            }
        });
    }
    // Downloading Img From Url In External Storage
    class MyDownloadTask extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            try {
                DownloadImageFromPath(bitmap);
            } catch (IOException e) {
                Log.e("TESTING", logMsg+"onPostExecute: ",e);
            }
            finally {
                loadingPB.hide();
                Toast.makeText(SetWallpaperActivity.this, "Wallpaper Downloaded", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingPB.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(imgUrl);
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch(IOException e) {
                Log.e("TESTING", logMsg+"doInBackground: "+e);
            }
            return image;
        }
    }
    // Set Wallpaper Code
    class SetWallpaperTask extends AsyncTask<String, Void, Bitmap>{
        int value;

        public SetWallpaperTask(int value) {
            this.value = value;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            SetWallpaper(bitmap,value);
            loadingPB.hide();
            Toast.makeText(SetWallpaperActivity.this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingPB.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(imgUrl);
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            } catch(IOException e) {
                System.out.println(e);
            }

            return image;
        }
    }
    public void DownloadImageFromPath(Bitmap bmp) throws IOException {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                File myDir = new File(root + "/WallPic");
                Log.e("DIRECTORY",logMsg+myDir.getPath());

                if (!myDir.exists()) {
                    myDir.mkdirs();
                }

                String name =imageNumber+".jpg";
                myDir = new File(myDir, name);

                FileOutputStream out = new FileOutputStream(myDir);

        bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
        MediaScannerConnection.scanFile(getApplicationContext(), new String[] { myDir.getAbsolutePath() }, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
            }
        });

    }
    public void SetWallpaper(Bitmap img,int value){
        WallpaperManager wallpaperManager=WallpaperManager.getInstance(getApplicationContext());
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        Bitmap bitmap = Bitmap.createScaledBitmap(img, width, height, true);
        try {
            wallpaperManager.setBitmap(bitmap,null,false,value);
        } catch (IOException e) {
            Log.e("TESTING", logMsg+"SetWallpaper: "+e);
        }

    }
    public boolean checkPermission(){
        int ReadVideo= ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_MEDIA_IMAGES);
        return (ReadVideo==PackageManager.PERMISSION_GRANTED);
    }
    private  void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_MEDIA_IMAGES}, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100 && grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                downloadWallpaper_bt.callOnClick();
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
    public void sendToSettingDialog(){
        new AlertDialog.Builder(SetWallpaperActivity.this)
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