package com.example.WallPic.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.WallPic.ApiService;
import com.example.WallPic.AppData;
import com.example.WallPic.MyAdapters.CommonAdapter;
import com.example.WallPic.MyModels.MyModel;
import com.example.WallPic.MyModels.Photo;
import com.example.WallPic.R;
import com.example.WallPic.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView wallpaperImg;
    String logMsg="This Msg In HomeFragment ";
    List<Photo>data;
    public static CommonAdapter commonAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        wallpaperImg=view.findViewById(R.id.Wallpaper_image_rcv);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Fetching Data From Api
        ApiService service= RetrofitInstance.API_instance().create(ApiService.class);
        service.getPost(AppData.ApiKey,"Mobile Wallpaper",20).enqueue(new Callback<MyModel>() {
            @Override
            public void onResponse(Call<MyModel> call, Response<MyModel> response) {
                if(response.code()==200&&response.body()!=null){
                    Log.d("TESTING", logMsg+response.body().getTotalResults());
                    data=response.body().getPhotos();
                    Log.d("TESTING", logMsg+data.get(0).getUrl()+" ");
                    setAdapter();
                }

            }
            @Override
            public void onFailure(Call<MyModel> call, Throwable t) {

            }
        });
    }

    private void setAdapter() {
        commonAdapter =new CommonAdapter(getActivity(),data,"Mobile Wallpaper",false);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2,RecyclerView.VERTICAL,false);
        wallpaperImg.setLayoutManager(gridLayoutManager);
        wallpaperImg.setAdapter(commonAdapter);
    }

}