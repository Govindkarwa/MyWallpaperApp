package com.example.WallPic.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.WallPic.MyAdapters.DownloadAdapter;
import com.example.WallPic.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadFragment extends Fragment {
    String logMsg="This Msg In DownloadFragment ";
    RecyclerView download_rcv;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_download, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Reading & Checking DIRECTORY
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File myDir = new File(root + "/WallPic");
        if(myDir.exists()) {
            Log.d("Files", logMsg +"Path: " + myDir);
            File directory = new File(myDir.getPath());
            File[] files = directory.listFiles();
            List<String> filesPaths = new ArrayList<>();
            Log.d("Files", logMsg +"Size: " + files.length);
            for (int i = 0; i < files.length; i++) {
                filesPaths.add(files[i].getPath());
                Log.d("Files", logMsg +"FileName:" + files[i].getName());
            }

            DownloadAdapter downloadAdapter = new DownloadAdapter(getActivity(),filesPaths);
            download_rcv=view.findViewById(R.id.Wallpaper_image_rcv_download);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2,RecyclerView.VERTICAL,false);
            download_rcv.setLayoutManager(gridLayoutManager);
            download_rcv.setAdapter(downloadAdapter);
        }


    }
}