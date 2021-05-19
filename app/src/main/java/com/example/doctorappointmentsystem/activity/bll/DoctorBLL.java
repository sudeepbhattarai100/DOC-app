package com.example.doctorappointmentsystem.activity.bll;

import com.example.doctorappointmentsystem.api.doctor_api;
import com.example.doctorappointmentsystem.model.doctor;
import com.example.doctorappointmentsystem.model.patients;
import com.example.doctorappointmentsystem.serverResponse.doctorResponse;
import com.example.doctorappointmentsystem.serverResponse.patientResponse;
import com.example.doctorappointmentsystem.url.url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class DoctorBLL {
    boolean isSuccess = false;

    public boolean login(String username, String password) {
        doctor doc = new doctor(username, password);

        doctor_api doctor_api = url.getInstance().create(doctor_api.class);
        Call<doctorResponse> doctorResponseCall = doctor_api.login(doc);

        try {
            Response<doctorResponse> loginResponse = doctorResponseCall.execute();
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
