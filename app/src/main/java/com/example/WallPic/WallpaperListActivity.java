package com.example.WallPic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.WallPic.MyAdapters.CommonAdapter;
import com.example.WallPic.MyModels.CategoryModel;
import com.example.WallPic.MyModels.MyModel;
import com.example.WallPic.MyModels.Photo;
import com.example.WallPic.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WallpaperListActivity extends AppCompatActivity {
    RecyclerView wallpaperImg;
    TextView headingText;
    List<Photo> data;
    CategoryModel parameters;
    public static CommonAdapter ad;
    String logMsg="This Msg In WallpaperListActivity ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_list);
        wallpaperImg=findViewById(R.id.Cat_Wallpaper_Image_Rcv);
        headingText=findViewById(R.id.Cat_Heading);
        parameters = (CategoryModel) getIntent().getSerializableExtra("DataList");
        headingText.setText(parameters.getCategoryName());
        ApiService service= RetrofitInstance.API_instance().create(ApiService.class);
        service.getPost(AppData.ApiKey,parameters.getSearchData(),20).enqueue(new Callback<MyModel>() {
            @Override
            public void onResponse(Call<MyModel> call, Response<MyModel> response) {
                if(response.code()==200&&response.body()!=null){
                    Log.d("TESTING", logMsg+response.body().getTotalResults()+" ");
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
        ad=new CommonAdapter(WallpaperListActivity.this,data,parameters.getSearchData(),true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(WallpaperListActivity.this,2,RecyclerView.VERTICAL,false);
        wallpaperImg.setLayoutManager(gridLayoutManager);
        wallpaperImg.setAdapter(ad);
    }
}