package com.example.WallPic;

import com.example.WallPic.MyModels.MyModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {

    @GET("v1/search")
    Call<MyModel> getPost(@Header("Authorization") String authHeader, @Query("query") String type, @Query("per_page") int NoOfPhotos);
    @GET("v1/search")
    Call<MyModel> getPostByPage(@Header("Authorization") String authHeader, @Query("page") int pageNo, @Query("query") String type, @Query("per_page") int NoOfPhotos);

}
