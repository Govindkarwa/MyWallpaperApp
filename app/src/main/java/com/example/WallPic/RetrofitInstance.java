package com.example.WallPic;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {


    public static Retrofit API_instance(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pexels.com/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())// Optional: for JSON parsing using Gson
                .build();

        return  retrofit;

    }

}
