package com.example.doctorappointmentsystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctorappointmentsystem.R;
import com.example.doctorappointmentsystem.api.doctor_api;
import com.example.doctorappointmentsystem.model.doctor;
import com.example.doctorappointmentsystem.serverResponse.doctorResponse;

import com.example.doctorappointmentsystem.url.url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocLoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin, btnRegister;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_login);

        btnRegister=findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                login();
                break;

            case R.id.btnRegister:
                openRegister();
                break;

                default:
                break;
        }
    }
    private void login() {
        if(username.getText().toString()!=null && password.getText().toString()!=null){
            doctor doctorLogin = new doctor(username.getText().toString(), password.getText().toString());
            doctor_api doctorApi = url.getInstance().create(doctor_api.class);
            Call<doctorResponse> loginCall = doctorApi.login(doctorLogin);

            loginCall.enqueue(new Callback<doctorResponse>() {
                @Override
                public void onResponse(Call<doctorResponse> call, Response<doctorResponse> response) {
                  if(!response.isSuccessful()){
                        Toast.makeText(DocLoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    url.token += response.body().getToken();
                    openDashboard();
                }

                @Override
                public void onFailure(Call<doctorResponse> call, Throwable t) {
                    Toast.makeText(DocLoginActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }



    }


    public void openDashboard(){
        Intent openDashboard = new Intent(DocLoginActivity.this, docMainActivity.class);
        startActivity(openDashboard);
    }

    public void openRegister(){
        Intent intent = new Intent(DocLoginActivity.this, DocRegisterActivity.class);
        startActivity(intent);
    }

}
