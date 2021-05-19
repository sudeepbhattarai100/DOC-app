package com.example.docapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docapp.api.doctor_api;
import com.example.docapp.model.patients;
import com.example.docapp.serverResponse.patientResponse;
import com.example.docapp.url.url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends WearableActivity {


    private EditText username,password;
    private Button btnLogin;
//    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnLogin = findViewById(R.id.btnLogin);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });




    }







    private void login() {
        if(username.getText().toString()!=null && password.getText().toString()!=null){
            patients patientLogin = new patients(username.getText().toString(), password.getText().toString());
            doctor_api patientApi = url.getInstance().create(doctor_api.class);
            Call<patientResponse> loginCall = patientApi.login(patientLogin);

            loginCall.enqueue(new Callback<patientResponse>() {
                @Override
                public void onResponse(Call<patientResponse> call, Response<patientResponse> response) {

                    if(!response.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Username and password didn't match", Toast.LENGTH_SHORT).show();
                      //  vibrator.vibrate(50);
                        return;
                    }

                    url.token += response.body().getToken();
                    openDashboard();
                }

                @Override
                public void onFailure(Call<patientResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Username and password didn't match" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }




    }

    public void openDashboard(){
        Intent openDashboard = new Intent(MainActivity.this, dashboard.class);
        startActivity(openDashboard);
    }



}
