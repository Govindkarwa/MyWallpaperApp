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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class IntroScreen extends AppCompatActivity {
    Button startBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);
        startBT=findViewById(R.id.Start_bt);
        startBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()){
                    Toast.makeText(IntroScreen.this,"Permission Granted",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(IntroScreen.this,MainScreen.class);
                    startActivity(i);
                }else {
                    requestPermission();
                }
            }
        });
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
                startBT.callOnClick();
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
        new AlertDialog.Builder(IntroScreen.this)
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