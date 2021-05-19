package com.example.doctorappointmentsystem.activity.bll;

import com.example.doctorappointmentsystem.api.doctor_api;
import com.example.doctorappointmentsystem.model.patients;
import com.example.doctorappointmentsystem.serverResponse.patientResponse;
import com.example.doctorappointmentsystem.url.url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginBLL {


    boolean isSuccess = false;

    public boolean login(String username, String password) {
        patients user = new patients(username, password);

        doctor_api rideshareApi = url.getInstance().create(doctor_api.class);
        Call<patientResponse> usersCall = rideshareApi.login(user);

        try {
            Response<patientResponse> loginResponse = usersCall.execute();
            if (loginResponse.isSuccessful()) {

                url.token += loginResponse.body().getToken();
                // Url.Cookie = imageResponseResponse.headers().get("Set-Cookie");
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
