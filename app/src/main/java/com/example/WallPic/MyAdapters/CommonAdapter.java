package com.example.WallPic.MyAdapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.WallPic.ApiService;
import com.example.WallPic.AppData;
import com.example.WallPic.MyModels.MyModel;
import com.example.WallPic.MyModels.Photo;
import com.example.WallPic.R;
import com.example.WallPic.RetrofitInstance;
import com.example.WallPic.SetWallpaperActivity;
import com.example.WallPic.WallpaperListActivity;
import com.example.WallPic.fragment.HomeFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonAdapter extends  RecyclerView.Adapter<MyViewHolder>{
    Context context;
    List<Photo> data;
    String CatName;
    String logMsg="This Msg In CommonAdapter ";
    Boolean isActivity;
    int pageNo=2;
    public CommonAdapter(Context context, List<Photo> data, String CatName,Boolean isActivity) {
        this.context = context;
        this.data = data;
        this.CatName = CatName;
        this.isActivity=isActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comon_wallpaper_item_view,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context)
                .load(data.get(position).getSrc().getPortrait())
                .placeholder(R.drawable.wallpaper_app_logo)
                .error(R.drawable.wallpaper)
                .into(holder.Wallpaper);
        holder.Wallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SetWallpaperActivity.class);
                i.putExtra("URL",data.get(position).getSrc().getPortrait());
                i.putExtra("ImgNo",data.get(position).getId());
                context.startActivity(i);
            }
        });

        // Fetching More Data From Api And Updating Adapter

         if (position==(data.size()-1)) {
                ApiService service = RetrofitInstance.API_instance().create(ApiService.class);
                service.getPostByPage(AppData.ApiKey, pageNo, CatName, 20).enqueue(new Callback<MyModel>() {
                    @Override
                    public void onResponse(Call<MyModel> call, Response<MyModel> response) {
                        if (response.code() == 200 && response.body() != null) {
                            Log.d("TESTING", logMsg+response.body().getTotalResults() + " ");
                            List<Photo> tempData = response.body().getPhotos();
                            Log.d("TESTING", logMsg+data.get(0).getUrl() + " ");
                            data.addAll(tempData);
                            pageNo++;
                            Log.d("TESTING", logMsg+data.size() +" And Page No Is "+pageNo);
                            if(isActivity){
                                WallpaperListActivity.ad.notifyDataSetChanged();
                            }else {
                                HomeFragment.commonAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<MyModel> call, Throwable t) {

                    }
                });
        }
    }

    @Override
    public int getItemCount() {
        if(data==null){
            return 0;
        }else {
        return data.size();
        }
    }
}
class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView Wallpaper;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        Wallpaper=itemView.findViewById(R.id.item_wallpaper);
    }
}

