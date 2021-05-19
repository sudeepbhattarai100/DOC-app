package com.example.doctorappointmentsystem.api;

import com.example.doctorappointmentsystem.model.category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface category_api {
    @GET("/speciality")
    Call<List<category>> getAllCategories(@Header("Authorization") String token);

    @GET("/speciality")
        Call<List<category>> getAllCategorieName();

    @GET("/speciality/getSpeciality")
    Call<List<category>> getAllinfo();
}
