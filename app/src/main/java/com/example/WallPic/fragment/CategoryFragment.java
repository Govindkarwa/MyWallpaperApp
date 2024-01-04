package com.example.WallPic.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.WallPic.MyAdapters.CategoryAdapter;
import com.example.WallPic.MyModels.CategoryModel;
import com.example.WallPic.R;
import com.example.WallPic.WallpaperListActivity;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    RecyclerView category_rcv;

    String logMsg="This Msg In CategoryFragment ";
    ArrayList<CategoryModel> data;
    androidx.appcompat.widget.SearchView  searchView;
    CategoryAdapter categoryAdapter;
    public CategoryFragment() {
        data=new ArrayList<>();
        data.add(new CategoryModel(R.drawable.nature,"Nature","nature Wallpapers"));
        data.add(new CategoryModel(R.drawable.animal,"Animals","animal Wallpapers"));
        data.add(new CategoryModel(R.drawable.colours,"Colours","colour Wallpapers"));
        data.add(new CategoryModel(R.drawable.sky,"Sky","Sky wallpapers"));
        data.add(new CategoryModel(R.drawable.games,"Games","Games Wallpaper"));
        data.add(new CategoryModel(R.drawable.designs1,"Designs","Designs Wallpaper"));
        data.add(new CategoryModel(R.drawable.abstractwallpaper,"Abstract","abstract"));
        data.add(new CategoryModel(R.drawable.dark,"Dark","Dark Wallpapers"));
        data.add(new CategoryModel(R.drawable.galaxy,"Galaxy","galaxy wallpaper"));
        data.add(new CategoryModel(R.drawable.holidays,"Holidays","Holidays Wallpapers"));
        data.add(new CategoryModel(R.drawable.car,"Cars","Car Wallpapers"));
        data.add(new CategoryModel(R.drawable.space,"Space","Space Wallpaper"));
        data.add(new CategoryModel(R.drawable.music,"Music","Music Wallpaper"));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        category_rcv=view.findViewById(R.id.category_rcv);
        searchView=view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getActivity(),query,Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), WallpaperListActivity.class);
                i.putExtra("DataList",new CategoryModel(R.drawable.wallpaper,query.toUpperCase(),query));
                getActivity().startActivity(i);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        categoryAdapter =new CategoryAdapter(getActivity(),data);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2, RecyclerView.VERTICAL,false);
        category_rcv.setLayoutManager(gridLayoutManager);
        category_rcv.setAdapter(categoryAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }
}