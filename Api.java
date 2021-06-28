package com.example.mymachinetest;

import com.example.mymachinetest.Model.NetResponse;
import com.example.mymachinetest.Model.RequestModel;
import com.example.mymachinetest.Model.Requestmodel2;
import com.example.mymachinetest.Model.dataModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {


    @POST("User/Login")
    Call<NetResponse> UserLogin(@Body RequestModel requestdata);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("report/GetPetList")
    Call<NetResponse> GetPetList(
            @Body Requestmodel2 requestdata2,
            @Header("Authorization") String token);



}
