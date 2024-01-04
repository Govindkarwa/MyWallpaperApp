package com.example.WallPic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.WallPic.fragment.CategoryFragment;
import com.example.WallPic.fragment.DownloadFragment;
import com.example.WallPic.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainScreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationBar;
    HomeFragment homeFragment;
    DownloadFragment downloadFragment;
    CategoryFragment categoryFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        bottomNavigationBar=findViewById(R.id.bottomNavigation_bar);

        homeFragment=new HomeFragment();
        downloadFragment=new DownloadFragment();
        categoryFragment=new CategoryFragment();
        if(!checkPermission()){
            requestPermission();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_view, homeFragment)
                .commit();
        bottomNavigationBar.setOnNavigationItemSelectedListener(this);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.homeFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_view, homeFragment)
                    .commit();
            return true;
        }else if(id==R.id.categoryFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_view, categoryFragment)
                    .commit();
            return true;
        }else if(id==R.id.downloadFragment) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_view,downloadFragment)
                        .commit();
                return true;
        }
        return false;
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
                Toast.makeText(this,"Permission not Granted",Toast.LENGTH_SHORT).show();
                sendToSettingDialog();
            }
        }
        else {
            Toast.makeText(this,"Permission not Granted",Toast.LENGTH_SHORT).show();
            sendToSettingDialog();
        }
    }
    // Dialog Box To Application Setting For Enable Permission
    public void sendToSettingDialog(){
        new AlertDialog.Builder(MainScreen.this)
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