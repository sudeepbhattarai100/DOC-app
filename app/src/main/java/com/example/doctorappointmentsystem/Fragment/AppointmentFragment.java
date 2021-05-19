package com.example.doctorappointmentsystem.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorappointmentsystem.R;
import com.example.doctorappointmentsystem.adapter.CategoryAdapter;
import com.example.doctorappointmentsystem.adapter.DoctorAdapter;
import com.example.doctorappointmentsystem.api.category_api;
import com.example.doctorappointmentsystem.model.category;
import com.example.doctorappointmentsystem.model.doctor;
import com.example.doctorappointmentsystem.url.url;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentFragment extends Fragment {

    RecyclerView rv;
    private LinearLayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_appointment, container, false);


        rv = v.findViewById(R.id.rvCategories);
        try {
            category_api getCategories = url.getInstance().create(category_api.class);
            Call<List<category>> categoryCall = getCategories.getAllCategories(url.token);

            categoryCall.enqueue(new Callback<List<category>>() {
                @Override
                public void onResponse(Call<List<category>> call, Response<List<category>> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(getActivity(), "Couldn't get categories", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        List<category> categoryList = response.body();
                        layoutManager = new GridLayoutManager(getContext(),2);
                        rv.setLayoutManager(layoutManager);
                        CategoryAdapter catAdapter = new CategoryAdapter(getContext(), categoryList);
                        rv.setAdapter(catAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<category>> call, Throwable t) {
                    Toast.makeText(getContext(), "Error : "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return  v;
    }
}
