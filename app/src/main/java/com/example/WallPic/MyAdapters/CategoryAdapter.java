package com.example.WallPic.MyAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WallPic.MyModels.CategoryModel;
import com.example.WallPic.R;
import com.example.WallPic.WallpaperListActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    Context context;
    List<CategoryModel> parameters;

    public CategoryAdapter(Context context, List<CategoryModel> parameters) {
        this.context = context;
        this.parameters = parameters;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.category_item_view,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.cat_Image.setImageResource(parameters.get(position).getImageId());
        holder.cat_Name.setText(parameters.get(position).getCategoryName());
        holder.cat_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, WallpaperListActivity.class);
                i.putExtra("DataList",parameters.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parameters.size();
    }

}
class CategoryViewHolder extends RecyclerView.ViewHolder {
    TextView cat_Name;
    ImageView cat_Image;
    MaterialCardView cat_card;
    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        cat_Name=itemView.findViewById(R.id.main_heading_cat);
        cat_Image=itemView.findViewById(R.id.main_img_cat);
        cat_card=itemView.findViewById(R.id.main_card_cat);
        cat_card.setRadius(14);
    }
}
