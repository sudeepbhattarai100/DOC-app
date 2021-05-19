package com.example.doctorappointmentsystem.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorappointmentsystem.R;
import com.example.doctorappointmentsystem.adapter.DoctorAdapter;
import com.example.doctorappointmentsystem.api.doctor_api;
import com.example.doctorappointmentsystem.model.doctor;
import com.example.doctorappointmentsystem.url.url;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {


    RecyclerView rv;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Bundle bundle = getIntent().getExtras();
        Context context = getApplicationContext();
        String catname = "empty";
        if (bundle != null) {
            catname = bundle.getString("category");
        }


        Toast.makeText(context, "The category data is " + catname, Toast.LENGTH_SHORT).show();
        rv = findViewById(R.id.rvDoctor);
        try {
            doctor_api getDoctor = url.getInstance().create(doctor_api.class);
            Call<List<doctor>> docCall = getDoctor.getDoctorByCategory(url.token, catname);

            docCall.enqueue(new Callback<List<doctor>>() {
                @Override
                public void onResponse(Call<List<doctor>> call, Response<List<doctor>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(BookingActivity.this, "Couldn't get categories", Toast.LENGTH_SHORT).show();
                    } else {
                        List<doctor> doctorList = response.body();
                        layoutManager = new GridLayoutManager(BookingActivity.this,2);
                        rv.setLayoutManager(layoutManager);
                        DoctorAdapter docAdapter = new DoctorAdapter(BookingActivity.this, doctorList);

                        rv.setAdapter(docAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<doctor>> call, Throwable t) {
                    Toast.makeText(BookingActivity.this, "Error : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
