package com.example.doctorappointmentsystem.url;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class url {
   public static final String BASE_URL = "http://10.0.2.2:3002/";
  //  public static final String BASE_URL = "http://192.168.137.1:3000/";
    public static String imagePath = BASE_URL + "uploads/" ;
    public static String token = "Bearer ";


    public static Retrofit getInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return  retrofit;

    }

}
