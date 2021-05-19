package com.example.doctorappointmentsystem.activity.bll;

import com.example.doctorappointmentsystem.api.doctor_api;
import com.example.doctorappointmentsystem.model.patients;
import com.example.doctorappointmentsystem.serverResponse.patientResponse;
import com.example.doctorappointmentsystem.url.url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class SignupBLL {



    boolean isSuccess = false;

    public boolean login(String email,String username, String firstname, String password, String lastname, String address, String profileImage) {
        patients user = new patients(email, username, firstname,password, lastname,address, profileImage );

        doctor_api doctorApi = url.getInstance().create(doctor_api.class);
        Call<patientResponse> usersCall = doctorApi.signup(user);

        try {
            Response<patientResponse> loginResponse = usersCall.execute();
            if (loginResponse.isSuccessful()) {


                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
