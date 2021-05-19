package com.example.docapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.wear.widget.CircledImageView;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docapp.api.doctor_api;
import com.example.docapp.serverResponse.patientResponse;
import com.example.docapp.url.url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dashboard extends AppCompatActivity {

    private DrawerLayout drawer;
    TextView fullName, email;
    ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fullName= findViewById(R.id.fullName);
        email= findViewById(R.id.email);
        profilePic= findViewById(R.id.navProfilePic);
        loadCurrentUser();
    }

    private void loadCurrentUser(){
        doctor_api userDetails = url.getInstance().create(doctor_api.class);
        Call<patientResponse> callUser = userDetails.getUserDetails(url.token);

        callUser.enqueue(new Callback<patientResponse>() {
            @Override
            public void onResponse(Call<patientResponse> call, Response<patientResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText( dashboard.this, "Error : Couldn't get user", Toast.LENGTH_SHORT).show();
                }

                String imgPath = url.imagePath +  response.body().getProfileImage();

                Picasso.get().load(imgPath).into(profilePic);
                fullName.setText(response.body().getFirstName() +" "+response.body().getLastName());
                email.setText(response.body().getEmail());

                System.out.println("The image path is " + profilePic);
            }

            @Override
            public void onFailure(Call<patientResponse> call, Throwable t) {

            }
        });
    }

}

