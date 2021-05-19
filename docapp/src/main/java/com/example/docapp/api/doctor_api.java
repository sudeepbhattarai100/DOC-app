package com.example.docapp.api;



import com.example.docapp.model.patients;
import com.example.docapp.serverResponse.doctorResponse;
import com.example.docapp.serverResponse.patientResponse;
import com.example.docapp.serverResponse.picResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface doctor_api {
    @Multipart
    @POST("upload")
    Call<picResponse> uploadPic(@Part MultipartBody.Part file);

    @POST("patient/signup")
    Call<patientResponse> signup(@Body patients user);


    @POST ("patient/login")
    Call<patientResponse> login(@Body patients user);

    @GET ("patient/me")
    Call<patientResponse> getUserDetails(@Header("Authorization") String token);

    @GET ("doctor/me")
    Call<doctorResponse> getDoctorDetails(@Header("Authorization") String token);
}
