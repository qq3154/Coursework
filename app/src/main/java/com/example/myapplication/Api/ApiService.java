package com.example.myapplication.Api;

import com.example.myapplication.Upload.UploadResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {


    //Create a converter
    //      add to the service class to convert the response
    Gson gson = new GsonBuilder()
            //this is optional
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();


    //Why not use the localhost???
    //because the app is run on android emulator and localhost in the emulator refers to the device's own loopback service
    //to access the actual machine we can use 10.0.2.2
    ApiService apiService =  new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:61421/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @FormUrlEncoded
    @POST("COMP1424CW")
    Call<UploadResponse> postRequest(@Field("jsonpayload") String payload);
}
