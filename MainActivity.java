package com.example.mymachinetest;

import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymachinetest.Model.ListItem;
import com.example.mymachinetest.Model.NetResponse;
import com.example.mymachinetest.Model.RequestModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private Button btnlogin;
    private EditText email, password;
    String mailText, passText;
    String BASE_URL = "https://petofyapi.azurewebsites.net/api/";
    String TAG = "Mainactivity";
//    List<RequestModel> temp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnlogin = findViewById(R.id.loginbtn);
        email = findViewById(R.id.emailET);
        password = findViewById(R.id.PassET);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();
            }
        });

    }

    public void userlogin() {

        mailText = email.getText().toString().trim();
        passText = password.getText().toString().trim();
        ListItem listItem = new ListItem(mailText, passText);
        RequestModel requestModel = new RequestModel(listItem);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Api apiInterface = retrofit.create(Api.class);
        Call<NetResponse> netResponseCall = apiInterface.UserLogin(requestModel);
        netResponseCall.enqueue(new Callback<NetResponse>() {
            @Override
            public void onResponse(Call<NetResponse> call, Response<NetResponse> response) {
                if (response.isSuccessful()) {
                    String sendToken=response.body().getResponse().getToken().toString();
                    SharedPreferences sharedPreferences=getSharedPreferences("token",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("token",sendToken);
                    editor.apply();


                    Intent intent=new Intent(MainActivity.this,Dashboard.class);
                    startActivity(intent);

                    Log.d(TAG, "onResponse: " + response.body().getData().getAddress().toString());
                    Log.d(TAG, "onResponse: " + response.body().getResponse().getToken());
                }

            }

            @Override
            public void onFailure(Call<NetResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onfailure method", Toast.LENGTH_LONG).show();

            }
        });

    }

    void uservalidation() {
        String mail = email.getText().toString().trim();
        String pss = password.getText().toString().trim();

        if (mail.isEmpty()) {
            email.setError("email cant be empty");
            email.requestFocus();
            return;
        }
        if (Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }

        if (pss.isEmpty()) {
            password.setError("Password cannott be empty");
            password.requestFocus();
            return;
        }


    }


}