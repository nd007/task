package com.example.mymachinetest;

import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymachinetest.Model.ListItem;
import com.example.mymachinetest.Model.NetResponse;
import com.example.mymachinetest.Model.RequestModel;
import com.example.mymachinetest.Model.Requestmodel2;
import com.example.mymachinetest.Model.dashboardModel;
import com.example.mymachinetest.Model.dataModel;
import com.example.mymachinetest.Model.petListmodel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Dashboard extends AppCompatActivity {
    //    private TextView second_result;
    String BASE_URL = "https://petofyapi.azurewebsites.net/api/";
    String TAG = "Dashboard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "this is default value");
        dashboardModel dashboardModel = new dashboardModel(1, 20);
        Requestmodel2 requestmodel2 = new Requestmodel2(dashboardModel);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api apiInterface = retrofit.create(Api.class);
        Call<NetResponse> responseCall = apiInterface.GetPetList(requestmodel2,token);

        responseCall.enqueue(new Callback<NetResponse>() {
            @Override
            public void onResponse(Call<NetResponse> call, Response<NetResponse> response) {
                Log.d(TAG, "onResponse: dashboard  " + response.body().getData().getPetListmodel().get(2).toString());
                ArrayList<petListmodel> listpet=response.body().getData().getPetListmodel();
                for(int i=0;i<listpet.size();i++)
                {

                    Log.d(TAG, "onResponse: dashboard  " +listpet.get(i).getPetName());
                    Log.d(TAG, "onResponse: dashboard  " +listpet.get(i).getDateOfBirth());
                    Log.d(TAG, "onResponse: dashboard  " +listpet.get(i).getPetParentName());
                    Log.d(TAG, "onResponse: dashboard  " +listpet.get(i).getId());
                }
                DataListAdapter postsAdapter=new DataListAdapter(getApplicationContext(),listpet);
                recyclerView.setAdapter(postsAdapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(Dashboard.this,
                        DividerItemDecoration.HORIZONTAL));
                postsAdapter.notifyDataSetChanged();



            }

            @Override
            public void onFailure(Call<NetResponse> call, Throwable t) {
                Log.d(TAG, "onfailure: dashboard  " + t.getMessage());
                Toast.makeText(getApplicationContext(), "onfailure dashboard"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}