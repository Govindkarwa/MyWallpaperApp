package com.example.WallPic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.WallPic.MyModels.MyModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {
    Button callApi;
    ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        callApi=findViewById(R.id.CallApi);
        img1=findViewById(R.id.img);
        Glide.with(MainActivity2.this)
                .load("https://images.pexels.com/photos/3573351/pexels-photo-3573351.png?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800")
                .error(R.drawable.wallpaper)
                .into(img1);
        callApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ApiData();
            }
        });

    }

    private void ApiData() {
        Log.d("TESTING", "onCreate:api");
        ApiService service=RetrofitInstance.API_instance().create(ApiService.class);
        service.getPost("nDx0NfEkioNIDZqOl1lO20uzT3kRgFA2wxlbI6iVTBEOa3hw0B0UJaSd","nature",10).enqueue(new Callback<MyModel>() {
            @Override
            public void onResponse(Call<MyModel> call, Response<MyModel> response) {
                Log.d("TESTING", "onCreate:api response");
                if(response.code()==200&&response.body()!=null){
                Log.d("TESTING", response.body().getTotalResults()+" ");}

            }

            @Override
            public void onFailure(Call<MyModel> call, Throwable t) {

            }
        });
    }
}