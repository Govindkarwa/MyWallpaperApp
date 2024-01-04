package com.example.WallPic.MyAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WallPic.R;
import com.example.WallPic.SetDownloadWallpaperActivity;

import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadViewHolder> {
    Context context;
    String logMsg="This Msg In DownloadAdapter ";
    List<String> data;

    public DownloadAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public DownloadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.download_wallpaper_item_view,parent,false);
        return new DownloadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadViewHolder holder, int position) {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(Uri.parse(data.get(position))));
            holder.wallpaperImg.setImageBitmap(bitmap);

        } catch (Exception e) {
            holder.wallpaperImg.setImageResource(R.drawable.wallpaper);
            Log.e("TESTING",logMsg+e);
            throw new RuntimeException(e);
        }
        holder.wallpaperImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SetDownloadWallpaperActivity.class);
                i.putExtra("Data",data.get(position));
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
class DownloadViewHolder extends RecyclerView.ViewHolder {
    ImageView wallpaperImg;
    public DownloadViewHolder(@NonNull View itemView) {
        super(itemView);
        wallpaperImg=itemView.findViewById(R.id.item_wallpaper_download);
    }
}
